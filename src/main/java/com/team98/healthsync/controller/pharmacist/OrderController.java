package com.team98.healthsync.controller.pharmacist;

import com.team98.healthsync.enums.OrderState;
import com.team98.healthsync.models.PharmacyOrder;
import com.team98.healthsync.repository.PharmacyOrderRepository;
import com.team98.healthsync.sms.TwilioMain;
import com.team98.healthsync.sms.TwillioHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class OrderController {

    @Autowired
    private PharmacyOrderRepository pharmacyOrderRepository;

    @GetMapping("/pharmacist/orders")
    @ResponseBody
    public Iterable<PharmacyOrder> getAllOrders(){
       return pharmacyOrderRepository.findAllByStateOrderByDateTimeDesc(OrderState.Received);
    }

    @GetMapping("/pharmacist/orders/{id}")
    public ModelAndView getCurrentOrders(@PathVariable("id") @Min(value = 0) @NotNull long id){
        PharmacyOrder order=pharmacyOrderRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",order.getPrescription().getAppointment().getChannel().getDoctor().getFirstName(), order.getPrescription().getAppointment().getChannel().getDoctor().getLastName());
        order.getPrescription().getAppointment().getChannel().getDoctor().setFullName(fullName);
        ModelAndView mv=new ModelAndView();
        mv.addObject("order",order);
        mv.setViewName("thymeleaf/pharmacist/order/view_order");
        return mv;
    }

    @GetMapping("/pharmacist/orders/{id}/{state}")
    @Transactional
    public ModelAndView getOrderState(@PathVariable("id") @Min(value = 0) @NotNull long id,
                                      @PathVariable("state") String state){
        PharmacyOrder order=pharmacyOrderRepository.findById(id).orElseThrow();

        if(state.equals(OrderState.Cancel.toString().toLowerCase())){
            order.setState(OrderState.Cancel);
            pharmacyOrderRepository.save(order);

        }
        else if(state.equals(OrderState.Complete.toString().toLowerCase())){
            order.setState(OrderState.Complete);
            pharmacyOrderRepository.save(order);

            String formattedContactNo = TwillioHelper.FormatSenderNumber(order.getPrescription().getAppointment().getPatient().getContactNo());
            String orderId=String.valueOf(order.getId());
            String message= "Order Id: " + orderId + ". Your order is ready. Please provide the order id and collect  from the pharmacy.";
            TwilioMain twilioMain=new TwilioMain(formattedContactNo,message);
            Boolean isSuccess=twilioMain.SendMessage();
        }


        return new ModelAndView("redirect:/pharmacist/order",state,"true");

    }



}
