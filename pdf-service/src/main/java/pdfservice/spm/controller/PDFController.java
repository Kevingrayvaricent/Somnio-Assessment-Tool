package pdfservice.spm.controller;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pdfservice.spm.pdf.SpmAnswers;
import pdfservice.spm.pdf.SpmPdfGenerator;

@RestController
@RequestMapping(path = "/pdf")
public class PDFController {
	Logger logger = Logger.getLogger(PDFController.class.getName());

	@Autowired
	private SpmPdfGenerator spmGen;
	
    @CrossOrigin
    @PostMapping(value = "/spm", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] spm(@RequestBody SpmAnswers answers) throws IOException {
        logger.info("Generate SPM PDF");
        byte[] pdf = spmGen.generatePDF(answers);
        return pdf;
    }

}