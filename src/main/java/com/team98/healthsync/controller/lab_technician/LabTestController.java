package com.team98.healthsync.controller.lab_technician;
import com.team98.healthsync.models.LabTest;
import com.team98.healthsync.models.LabTestCategory;
import com.team98.healthsync.models.Patient;
import com.team98.healthsync.repository.LabTestCategoryRepository;
import com.team98.healthsync.repository.LabTestRepository;
import com.team98.healthsync.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;

@Controller
public class LabTestController {

    @Autowired
    private LabTestCategoryRepository labTestCategoryRepository;
    @Autowired
    private LabTestRepository labTestRepository;
    @Autowired
    private PatientRepository patientRepository;

//    @Value("${blob-connection-string}")
//    private String connectionString;
//
//    @Value("${blob-container-name}")
//    private String containerName;


    @GetMapping("/labtechnician/labtests")
    @ResponseBody
    public Iterable<LabTest> getAllLabTestReports(){
        return labTestRepository.findAll();
    }

    @GetMapping("/labtechnician/labtest/new")
    public ModelAndView newTest(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("labtest",new LabTest());
        mv.addObject("labtests_dropdown",labTestCategoryRepository.findAll());
        mv.setViewName("thymeleaf/lab_technician/labtest/new_labtest");
        return mv;
    }

    @PostMapping("/labtechnician/labtest")
    @Transactional
    public ModelAndView createNewLabTest(@Valid @ModelAttribute("labtest") LabTest labTest, BindingResult result){
        if(result.hasErrors()){
            ModelAndView mv=new ModelAndView();
            mv.addObject("labtest",labTest);
            mv.addObject("labtests_dropdown",labTestCategoryRepository.findAll());
            mv.setViewName("thymeleaf/lab_technician/labtest/new_labtest");
            return mv;
        }

        try{
            Patient patient=patientRepository.findById(Long.parseLong(labTest.getPatientId())).orElseThrow(()->new EntityNotFoundException());
            labTest.setPatient(patient);
            LabTestCategory category=labTestCategoryRepository.findById(labTest.getCategory().getId()).orElseThrow();
            labTest.setCategory(category);

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            Date dt=new Date(ts.getTime());
            labTest.setReceivedDate(dt);
            labTest.setCollectedDate(null);

            labTestRepository.save(labTest);

        }catch (NumberFormatException ex ){
            return new ModelAndView("redirect:/labtechnician/labtest", "error","Invalid Patient Id");
        }
        catch (EntityNotFoundException ex ) {
            return new ModelAndView("thymeleaf/lab_technician/labtest/new_labtest", "error", "Invalid Patient Id");
        }
        return new ModelAndView("redirect:/labtechnician/labtest","success","true");

    }

//    @GetMapping("/labtechnician/labtests/{id}")
//    public  ModelAndView getlabTestById(@PathVariable("id") @NotNull @Min(value = 0) long id){
//        LabTest report=labTestRepository.findById(id).orElseThrow();
//        ModelAndView mv=new ModelAndView();
//        mv.addObject("report",report);
//        mv.setViewName("thymeleaf/lab_technician/labtest/view_labtest");
//        return mv;
//    }
//
//
//    @GetMapping("/labtechnician/labtests/edit/{id}")
//    public ModelAndView editlabTestById(@PathVariable("id") @NotNull @Min(value = 0) long id){
//        Optional<LabTest> labTest=labTestRepository.findById(id);
//        if(labTest.isPresent()==false){
//            return new ModelAndView("thymeleaf/lab_technician/labtest/labtests", "error", "Lab Test do not exit.");
//        }
//        return new ModelAndView("thymeleaf/lab_technician/labtest/edit_labtest","labtest",labTest);
//    }
//
//    @PostMapping("/labtechnician/labtests/edit")
//    @Transactional
//    public ModelAndView editLabTest(@RequestParam("report") MultipartFile file,
//                                    @RequestParam("state") LabTestState state,
//                                    @RequestParam("id") String id) throws IOException {
//
//        if((file.getSize()/(1024 * 1024))>5l){
//            return new ModelAndView("redirect:/labtechnician/labtest","filesizeerror","true");
//        }
//        if(!(file.getContentType().equals("application/pdf"))){
//            return new ModelAndView("redirect:/labtechnician/labtest","filetypeerror","true");
//        }
//        LabTest existingLabTest=labTestRepository.findById(Long.parseLong(id)).orElseThrow();
//        existingLabTest.setState(state);
//        existingLabTest.setPatientId(existingLabTest.getPatient().getId().toString());
//
//        if(file.getSize()!=0){
//            String fileName= UUID.randomUUID().toString() + ".pdf";
//            BlobContainerClient container = new BlobContainerClientBuilder()
//                    .connectionString(connectionString)
//                    .containerName(containerName)
//                    .buildClient();
//
//            BlobClient blob=container.getBlobClient(fileName);
//            blob.upload(file.getInputStream(),file.getSize(),true);
//            existingLabTest.setFileName(fileName);
//        }
//
//        if(state== LabTestState.Collected){
//            Timestamp ts = new Timestamp(System.currentTimeMillis());
//            Date dt=new Date(ts.getTime());
//            existingLabTest.setCollectedDate(dt);
//        }else if(state==LabTestState.Complete){
//            String formattedContactNo = TwillioHelper.FormatSenderNumber(existingLabTest.getPatient().getContactNo());
//            String testId=String.valueOf(existingLabTest.getId());
//            String message= "Report Id: " + testId + ". Your report is ready. Please collect from the Hospital.";
//            TwilioMain twilioMain=new TwilioMain(formattedContactNo,message);
//            Boolean isSuccess=twilioMain.SendMessage();
//        }
//
//        labTestRepository.save(existingLabTest);
//        return new ModelAndView("redirect:/labtechnician/labtest","update","true");
//    }





}
