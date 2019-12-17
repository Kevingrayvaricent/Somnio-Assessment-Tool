package pdfservice.spm.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import pdfservice.util.PdfUtil;
import pdfservice.util.RGBColor;

@Service
public class SpmPdfGenerator {
	Logger logger = Logger.getLogger(SpmPdfGenerator.class.getName());

	public byte[] generatePDF(SpmAnswers json) throws IOException {
		// Loading an existing document
		final String PDF_TEMPLATE = "templates/IBM_1793_SPM-results_clean.pdf";
		try (PDDocument doc = PDDocument.load(new FileInputStream(ResourceUtils.getFile("classpath:" +PDF_TEMPLATE)))) {

			System.out.println("PDF loaded");

			// Load font
			PDFont fontLight = PDType0Font.load(doc, new FileInputStream(ResourceUtils.getFile("classpath:font/IBMPlexSans-Light.ttf")));
			PDFont fontMedium = PDType0Font.load(doc, new FileInputStream(ResourceUtils.getFile("classpath:font/IBMPlexSans-Medium.ttf")));
			PDFont fontBold = PDType0Font.load(doc, new FileInputStream(ResourceUtils.getFile("classpath:font/IBMPlexSans-Bold.ttf")));
			PDFont fontRegular = PDType0Font.load(doc, new FileInputStream(ResourceUtils.getFile("classpath:font/IBMPlexSans-Regular.ttf")));
			
			// no border
			PDBorderStyleDictionary noBorder = new PDBorderStyleDictionary();
			noBorder.setWidth(0);
			
			// Blue color
			RGBColor blueColor = new RGBColor(109, 165, 253);

			// Get Page 2
			PDPage page2 = doc.getPage(1);
			try (PDPageContentStream contentPage2 = new PDPageContentStream(doc, page2, PDPageContentStream.AppendMode.APPEND, false)) {
				String bignumber = json.question1.replaceAll("[^\\d]", "");
				if (bignumber.length() == 0) { bignumber = "0"; }
				BigDecimal revenue = new BigDecimal(bignumber);
				// Write Question 1 value
				NumberFormat nf = new DecimalFormat("##,###,###");
				PdfUtil.writeLine(contentPage2, fontMedium, 9.0F, 36.6F, 544.0035F, "$" + nf.format(revenue), blueColor);
				
				String percentStr = json.question2.replaceAll("[^\\d]", "");
				int percent = 0;
				if (percentStr.length() > 0) { percent = Integer.valueOf(percentStr); }
				// Write Question 2 value
				PdfUtil.writeLine(contentPage2, fontMedium, 9.0F, 36.6F, 446.6897F, percentStr + "%", blueColor);
				
				// Calculate revenue (Q1 input) * over-payment (Q2 input) = total estimated overpayment
				BigDecimal overpay = new BigDecimal(0);
				if (percent > 0 && (!revenue.equals(0))) {
					overpay = revenue.multiply(new BigDecimal(percent / 100D));
				}
				
				PDAnnotationLink txtLink = new PDAnnotationLink();
				PDRectangle position = new PDRectangle();
				position.setLowerLeftX(337.0F);
				position.setLowerLeftY(521.0598F - 3f);
				position.setUpperRightX(515.0F);
				position.setUpperRightY(521.0598F + 12f);
				txtLink.setRectangle(position);
				txtLink.setBorderStyle(noBorder);
				
				PDAnnotationLink txtLink2 = new PDAnnotationLink();
				PDRectangle position2 = new PDRectangle();
				position2.setLowerLeftX(315.0F);
				position2.setLowerLeftY(508.0598F - 3f);
				position2.setUpperRightX(425.0F);
				position2.setUpperRightY(508.0598F + 12f);
				txtLink2.setRectangle(position2);
				txtLink2.setBorderStyle(noBorder);
				
				PDActionURI action = new PDActionURI();
				action.setURI("https://www.ibm.com/account/reg/us-en/signup?formid=urx-22202");
				txtLink.setAction(action);
				txtLink2.setAction(action);
				
				page2.getAnnotations().add(txtLink);
				page2.getAnnotations().add(txtLink2);
				
				// Write Q1/Q2 Result copy
				PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 612.0598F, "You estimated you might be paying ", percentStr + "%", " of revenue in ", Color.WHITE, blueColor);
				PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 599.0598F, "incentive compensation overpayments.  Based on your  ", Color.WHITE);
				PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 586.0598F, "last fiscal year’s revenue of ", "$" + nf.format(revenue), ", you could  ", Color.WHITE, blueColor);
				PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 573.0598F, "be overpaying incentive compensation by ", "$" + nf.format(overpay), ".  ", Color.WHITE, blueColor);
				PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 560.0598F, "This substantial amount is a concern to your bottom-line ", Color.WHITE);
				PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 547.0598F, "financials. You’re not alone. Read the Gartner Magic  ", Color.WHITE);
				PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 534.0598F, "Quadrant for Sales Performance Management report  ", Color.WHITE);
				PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 521.0598F, "here:", " https://www.ibm.com/account/reg/us-en/", "", Color.WHITE, blueColor);
				PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 508.0598F, "", "signup?formid=urx-22202", ".", Color.WHITE, blueColor);
				
				// Write Question 3 value
				String numPeople = json.question3.replaceAll("[^\\d]", "");
				long people = (numPeople.length() > 0) ? Long.valueOf(numPeople) : 0L;
				PdfUtil.writeLine(contentPage2, fontMedium, 9.0F, 36.6F, 219.81549F, nf.format(people), blueColor);
				
				// Write Question 4 value
				String answer = (json.question4 == 0) ? "Yes" : "No";
				PdfUtil.writeLine(contentPage2, fontMedium, 9.0F, 36.6F, 127.71863F, answer, blueColor);
				
				// Write Q3/Q4 Result copy
				if (json.question4 == 0) {
					PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 307.7516F, "You indicated you have ", nf.format(people), " sales representatives.   ", Color.BLACK, blueColor);
					PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 294.7516F, "You also indicated that ", answer.toLowerCase(), ", you will be hiring additional ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 281.7516F, "sales reps in the next three years. As your company grows ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 268.7516F, "and hires new salespeople, roles and specific territories will ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 255.75159F, "be defined and quota targets assigned, complicating your ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 242.75159F, "incentive compensation plan.", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 217.5116F, "Consider how to best support the number of reps you have in ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 204.5116F, "your sales organization. ", Color.BLACK);
				} else {
					PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 307.7516F, "You indicated you have ", nf.format(people), " sales representatives.   ", Color.BLACK, blueColor);
					PdfUtil.writeLine2Color(contentPage2, fontRegular, 9.0F, 315.0F, 294.7516F, "You also indicated that ", answer.toLowerCase(), ", you will not be hiring additional ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 281.7516F, "sales reps in the next three years. Organizations with 100 ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 268.7516F, "salespeople or more find that managing sales incentive ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 255.75159F, "compensation with spreadsheets and homegrown solutions ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 242.75159F, "results in errors in formulas, references and calculations, ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 229.75159F, "which can be difficult to find. ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 204.5116F, "Consider how to best support the number of reps you have in ", Color.BLACK);
					PdfUtil.writeLine(contentPage2, fontRegular, 9.0F, 315.0F, 191.5116F, "your sales organization. ", Color.BLACK);
				}
			}

			// Get Page 3
			PDPage page3 = doc.getPage(2);
			try (PDPageContentStream contentPage3 = new PDPageContentStream(doc, page3, PDPageContentStream.AppendMode.APPEND, false)) {
				
				// Write Question 5 answer
				int insentiveVal = json.question5;
				if (insentiveVal == 0) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 591.2661F, "— Spreadsheets and a manual process", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 591.2661F, "— Spreadsheets and a manual process", Color.BLACK); }
				if (insentiveVal == 1) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 574.7661F, "— A homegrown solution", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 574.7661F, "— A homegrown solution", Color.BLACK); }
				if (insentiveVal == 2) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 558.2661F, "— A CRM solution", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 558.2661F, "— A CRM solution", Color.BLACK); }
				if (insentiveVal == 3) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 541.7661F, "— An SPM vendor", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 541.7661F, "— An SPM vendor", Color.BLACK); }
				
				// Write Question 5 copy
				if (insentiveVal == 0) {
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 659.3223F, "You indicated you’re currently managing your sales incentive ", Color.BLACK);
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 646.3223F, "compensation leveraging ", "spreadsheets and a manual process", ". ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 633.3223F, "Almost every organization starts with spreadsheets. But once ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 620.3223F, "once an organization reaches more than 100 salespeople, ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 607.3223F, "spreadsheets become more error-prone and labor-intensive ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 594.3223F, "and lack flexibility, transparency and scalability. Chances are ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 581.3223F, "you won’t find the mistake until the money has left the ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 568.3223F, "business or there’s a lawsuit at your door.", Color.BLACK);
				}
				if (insentiveVal == 1) {
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 659.3223F, "You indicated you’re currently managing your sales incentive ", Color.BLACK);
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 646.3223F, "compensation leveraging ", "a homegrown solution", ". Sure, IT and ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 633.3223F, "development teams can develop a solution in house. But a ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 620.3223F, "market-responsive organization needs a highly flexible SPM ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 607.3223F, "system. In commercially sold applications, this is often the ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 594.3223F, "top consideration after feature strength. Challenges with ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 581.3223F, "going homegrown include maintenance and administration, ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 568.3223F, "accuracy, compliance, unclear analytic functions, ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 555.3223F, "development burden and lack of training.", Color.BLACK);
				}
				if (insentiveVal == 2) {
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 659.3223F, "You indicated you’re currently managing your sales incentive ", Color.BLACK);
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 646.3223F, "compensation leveraging ", "a CRM solution", ". Custom ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 633.3223F, "development on any platform almost always fails to deliver ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 620.3223F, "crucial functionality — because the platform is not fit for ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 607.3223F, "purpose. Smaller sales organizations investing six figures in ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 594.3223F, "the implementation phase and annual commitments of more ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 581.3223F, "than $1K per seat are common. But there are jobs to be ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 568.3223F, "done: capture and secure access to data, manage sales ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 555.3223F, "crediting rules, keep all participants up-to-date, model ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 542.3223F, "changes to the plan and much more.", Color.BLACK);
				}
				if (insentiveVal == 3) {
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 659.3223F, "You indicated you’re currently managing your sales incentive ", Color.BLACK);
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 646.3223F, "compensation leveraging an existing ", "SPM vendor", ". Many SPM ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 633.3223F, "vendors have purpose-built solutions to help manage the ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 620.3223F, "sales performance and incentive compensation management ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 607.3223F, "business processes. But it’s critical to define your business ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 594.3223F, "requirements in advance, or you risk making the wrong ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 581.3223F, "selection. Is your reporting and dashboard serving your ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 568.3223F, "user base? Can workflow processes be defined for quota ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 555.3223F, "setting, compensation plan agreements and territory ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 542.3223F, "assignment changes? Can you get more out of the solution, ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 529.3223F, "like advanced data analytics, artificial intelligence ", Color.BLACK);
					PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 516.3223F, "or data discovery?", Color.BLACK);
				}
				
				// Write Question 6 answer
				int manageSales = json.question6;
				if (manageSales == 0) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 219.69379F, "— 1-5", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 219.69379F, "— 1-5", Color.BLACK); }
				if (manageSales == 1) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 203.19379F, "— 6-9", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 203.19379F, "— 6-9", Color.BLACK); }
				if (manageSales == 2) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 186.69379F, "— 10-14", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 186.69379F, "— 10-14", Color.BLACK); }
				if (manageSales == 3) { PdfUtil.writeLine(contentPage3, fontMedium, 9.0F, 36.6F, 170.19379F, "— >15", blueColor); } else { PdfUtil.writeLine(contentPage3, fontLight, 9.0F, 36.6F, 170.19379F, "— >15", Color.BLACK); }
				
				
				// Write Question 6 copy
				if (manageSales == 0) {
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 307.4238F, "You indicated you have ", "1-5", " full-time employees ", Color.BLACK, blueColor);
				}
				if (manageSales == 1) {
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 307.4238F, "You indicated you have ", "6-9", " full-time employees ", Color.BLACK, blueColor);
				}
				if (manageSales == 2) {
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 307.4238F, "You indicated you have ", "10-14", " full-time employees ", Color.BLACK, blueColor);
				}
				if (manageSales == 3) {
					PdfUtil.writeLine2Color(contentPage3, fontRegular, 9.0F, 315.0F, 307.4238F, "You indicated you have ", "at least 15", " full-time employees ", Color.BLACK, blueColor);
				}
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 294.4238F, "managing your sales incentive compensation plans today. ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 281.4238F, "This number depends on the size of your sales organization, ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 268.4238F, "the complexity of your sales incentive business processes ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 255.4238F, "and the frequency of managing changes to your  ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 242.4238F, "incentive plans.", Color.BLACK);
				
				// Q6 static copy
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 217.18384F, "Your sales incentive compensation team could be ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 204.18384F, "overwhelmed — unable to focus on more strategic and  ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 191.18384F, "added value activities, or swamped with inquiries that come ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 178.18384F, "in from the sales team about how their compensation has ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 165.18384F, "been calculated (with no visibility into how the calculation ", Color.BLACK);
				PdfUtil.writeLine(contentPage3, fontRegular, 9.0F, 315.0F, 152.18384F, "was completed). ", Color.BLACK);
			}
			
			// Get Page 4
			PDPage page4 = doc.getPage(3);
			try (PDPageContentStream contentPage4 = new PDPageContentStream(doc, page4, PDPageContentStream.AppendMode.APPEND, false)) {
				// Write Question 7 answer
				int incentiveSchedule = json.question7;
				if (incentiveSchedule == 0) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 553.6938F, "— Monthly", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 553.6938F, "— Monthly", Color.WHITE); }
				if (incentiveSchedule == 1) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 537.1938F, "— Quarterly", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 537.1938F, "— Quarterly", Color.WHITE); }
				if (incentiveSchedule == 2) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 520.6938F, "— Annually", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 520.6938F, "— Annually", Color.WHITE); }
				
				// Write Question 7 copy
				float staticTesxtSpacing = 563.4238F - 25F;
				if (incentiveSchedule == 0) {
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 641.4238F, "You indicated you calculate and pay out sales incentive ", Color.WHITE);
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 315.0F, 628.4238F, "compensation to the sales team on a ", "monthly", " basis. Most ", Color.WHITE, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 615.4238F, "organizations calculate and pay out on a monthly basis, ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 602.4238F, "but they find this frequency to be an intense time. And often, ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 589.4238F, "find it takes days (or even weeks) to complete the business ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 576.4238F, "process, only to find themselves doing all the tasks and ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 563.4238F, "activities again for the next month.", Color.WHITE);
					
				}
				if (incentiveSchedule == 1) {
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 641.4238F, "You indicated you calculate and pay out sales incentive ", Color.WHITE);
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 315.0F, 628.4238F, "compensation to the sales team on a ", "quarterly", " basis. ", Color.WHITE, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 615.4238F, "Organizations paying sales incentive compensation on a ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 602.4238F, "quarterly basis still need to complete the common tasks ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 589.4238F, "and activities as those paying on a monthly basis.", Color.WHITE);
					
					staticTesxtSpacing = 589.4238F-25F;
				}
				if (incentiveSchedule == 2) {
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 641.4238F, "You indicated you calculate and pay out sales incentive ", Color.WHITE);
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 315.0F, 628.4238F, "compensation to the sales team on an ", "annual", " basis.  ", Color.WHITE, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 615.4238F, "Most organizations calculate and pay out on a monthly ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 602.4238F, "basis. It may be time for you to consider  ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 589.4238F, "monthly and/or quarterly compensation plans to better ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 576.4238F, "align with corporate goals and motivate the sales team to ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, 563.4238F, "focus on specific measures each month and/or quarter.", Color.WHITE);
				}
								
				// Write Question 7 static copy
				if (incentiveSchedule < 2) {
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, staticTesxtSpacing, "Your sales compensation team may feel the monthly payout ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, staticTesxtSpacing-13F, "business process is overwhelming, or too intense. ", Color.WHITE);
				} else {
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, staticTesxtSpacing, "Are you considering monthly and/or quarterly ", Color.WHITE);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315.0F, staticTesxtSpacing-13F, "compensation plans?", Color.WHITE);
				}
				
				// Write Question 8 answer
				int inqueryHours = json.question8;
				if (inqueryHours == 0) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 218.96643F, "— 1-6", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 218.96643F, "— 1-6", Color.BLACK); }
				if (inqueryHours == 1) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 202.46643F, "— 7-10", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 202.46643F, "— 7-10", Color.BLACK); }
				if (inqueryHours == 2) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 185.96643F, "— 11-20", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 185.96643F, "— 11-20", Color.BLACK); }
				if (inqueryHours == 3) { PdfUtil.writeLine(contentPage4, fontMedium, 9.0F, 36.6F, 169.46643F, "— >20", blueColor); } else { PdfUtil.writeLine(contentPage4, fontLight, 9.0F, 36.6F, 169.46643F, "— >20", Color.BLACK); }
				
				// Write Question 8 copy
				if (inqueryHours == 0) {
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 314.9302F, 304.6964F, "You indicated it’s taking between ", "1-6 hours", " to handle ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 291.6964F, "inquiries from the sales team. After paying out sales ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 278.6964F, "incentive compensation to the sales team, you know the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 265.6964F, "compensation team will receive inquiries from them. This ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 252.6964F, "can be very time-intensive — validating the transaction ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 239.6964F, "amount, determining how much time was spent on the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 226.69641F, "deal, checking for errors, etc.", Color.BLACK);
					
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 201.45642F, "Spending up to six hours handling inquiries seems reasonable. ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 188.45642F, "But as your sales team grows, the influx will become ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 175.45642F, "unmanageable. You may be in growth mode, planning to hire ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 162.45642F, "additional sales reps.", Color.BLACK);
				}
				if (inqueryHours == 1) {
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 314.9302F, 304.6964F, "You indicated it’s taking between ", "7-10 hours", " to handle ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 291.6964F, "inquiries from the sales team. After paying out sales ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 278.6964F, "incentive compensation to the sales team, you know the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 265.6964F, "compensation team will receive inquiries from them. This ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 252.6964F, "can be very time-intensive — validating the transaction ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 239.6964F, "amount, determining how much time was spent on the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 226.69641F, "deal, checking for errors, etc.", Color.BLACK);
					
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 201.45642F, "Spending up to 10 hours handling inquiries seems reasonable. ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 188.45642F, "But as your sales team grows, the influx will become ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 175.45642F, "unmanageable. You may be in growth mode, planning to hire ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 162.45642F, "additional sales reps.", Color.BLACK);
				}
				if (inqueryHours == 2) {
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 314.9302F, 304.6964F, "You indicated it’s taking between ", "11-20 hours", " to handle ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 291.6964F, "inquiries from the sales team. After paying out sales ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 278.6964F, "incentive compensation to the sales team, you know the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 265.6964F, "compensation team will receive inquiries from them. This ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 252.6964F, "can be very time-intensive — validating the transaction ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 239.6964F, "amount, determining how much time was spent on the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 226.69641F, "deal, checking for errors, etc.", Color.BLACK);
					
					
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 201.45642F, "Spending up to 20 hours handling inquiries is frustrating, ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 188.45642F, "and this time could be spent on more valuable tasks and ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 175.45642F, "activities for the business. The influx is likely unmanageable ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 162.45642F, "at this stage. ", Color.BLACK);
				}
				if (inqueryHours == 3) {
					PdfUtil.writeLine2Color(contentPage4, fontRegular, 9.0F, 314.9302F, 304.6964F, "You indicated it’s taking ", ">20 hours", " to handle ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 291.6964F, "inquiries from the sales team. After paying out sales ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 278.6964F, "incentive compensation to the sales team, you know the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 265.6964F, "compensation team will receive inquiries from them. This ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 252.6964F, "can be very time-intensive — validating the transaction ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 239.6964F, "amount, determining how much time was spent on the ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 314.9302F, 226.69641F, "deal, checking for errors, etc.", Color.BLACK);
					
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 201.45642F, "Spending more than 20 hours handling inquiries is frustrating,  ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 188.45642F, "and this time could be spent on more valuable tasks and ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 175.45642F, "activities for the business. The influx is likely unmanageable ", Color.BLACK);
					PdfUtil.writeLine(contentPage4, fontRegular, 9.0F, 315F, 162.45642F, "at this stage. ", Color.BLACK);
				}
			}
			
			// Get Page 5
			PDPage page5 = doc.getPage(4);
			try (PDPageContentStream contentPage5 = new PDPageContentStream(doc, page5, PDPageContentStream.AppendMode.APPEND, false)) {
				// Write Question 9 answer
				int confidence = json.question9;
				switch (confidence) {
					case 1:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "", "1 ", " 2  3  4  5  6  7  8  9  10", Color.BLACK, blueColor);
						break;
					case 2:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1 ", " 2 ", " 3  4  5  6  7  8  9  10", Color.BLACK, blueColor);
						break;
					case 3:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2 ", " 3 ", " 4  5  6  7  8  9  10", Color.BLACK, blueColor);
						break;
					case 4:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3 ", " 4 ", " 5  6  7  8  9  10", Color.BLACK, blueColor);
						break;
					case 5:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4 ", " 5 ", " 6  7  8  9  10", Color.BLACK, blueColor);
						break;
					case 6:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4  5 ", " 6 ", " 7  8  9  10", Color.BLACK, blueColor);
						break;
					case 7:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4  5  6 ", " 7 ", " 8  9  10", Color.BLACK, blueColor);
						break;
					case 8:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4  5  6  7 ", " 8 ", " 9  10", Color.BLACK, blueColor);
						break;
					case 9:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4  5  6  7  8 ", " 9 ", " 10", Color.BLACK, blueColor);
						break;
					case 10:
						PdfUtil.writeNumbers2Color(contentPage5, fontLight, fontMedium, 9.0F, 36.6F, 535.7904F, "1  2  3  4  5  6  7  8  9 ", " 10", "", Color.BLACK, blueColor);
						break;
				}
				
				// Write Question 9 copy
				if (confidence <= 3) {
					PdfUtil.writeLine2Color(contentPage5, fontRegular, 9.0F, 315.0F, 637.9666F, "You indicated your confidence level is ", "between 1-3", ". ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 624.9666F, "Accessing and analyzing the right data at the right time to ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 611.9666F, "make more informed, data-driven decisions has become a ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 598.9666F, "critical capability to remain competitive. Sales performance ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 585.9666F, "management data is a valuable source for decision making. ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 572.9666F, "Let’s face it, you’re using this sales data and other data ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 559.9666F, "sources to calculate incentive compensation. You’re paying ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 546.9666F, "the sales team, so the data needs to be trusted and accurate.", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 521.7266F, "Your confidence level is below 3. ", Color.BLACK);
				}
				if (confidence >= 4 && confidence <= 7) {
					PdfUtil.writeLine2Color(contentPage5, fontRegular, 9.0F, 315.0F, 637.9666F, "You indicated your confidence level is ", "between 4-7", ". ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 624.9666F, "Accessing and analyzing the right data at the right time to ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 611.9666F, "make more informed, data-driven decisions has become a ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 598.9666F, "critical capability to remain competitive. Sales performance ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 585.9666F, "management data is a valuable source for decision making. ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 572.9666F, "Let’s face it, you’re using this sales data and other data ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 559.9666F, "sources to calculate incentive compensation. You’re paying ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 546.9666F, "the sales team, so the data needs to be trusted and accurate.", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 521.7266F, "Your confidence level is between 4 and 7 — there's room ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 508.7266F, "for improvement.", Color.BLACK);
				}
				if (confidence >= 8) {
					PdfUtil.writeLine2Color(contentPage5, fontRegular, 9.0F, 315.0F, 637.9666F, "You indicated your confidence level is ", "between 8-10", ". ", Color.BLACK, blueColor);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 624.9666F, "Accessing and analyzing the right data at the right time to ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 611.9666F, "make more informed, data-driven decisions has become a ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 598.9666F, "critical capability to remain competitive. Sales performance ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 585.9666F, "management data is a valuable source for decision making. ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 572.9666F, "Let’s face it, you’re using this sales data and other data ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 559.9666F, "sources to calculate incentive compensation. You’re paying ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 546.9666F, "the sales team, so the data needs to be trusted and accurate.", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 521.7266F, "Your confidence level is between 8-10, you're probably ", Color.BLACK);
					PdfUtil.writeLine(contentPage5, fontRegular, 9.0F, 315.0F, 508.7266F, "accessing and analyzing data from varying data sources.", Color.BLACK);
				}
			}
			
			// page 6
			PDPage page6 = doc.getPage(5);
			try (PDPageContentStream contentPage6 = new PDPageContentStream(doc, page6, PDPageContentStream.AppendMode.APPEND, false)) {
				PDAnnotationLink txtLink = new PDAnnotationLink();
				PDRectangle position = new PDRectangle();
				position.setLowerLeftX(155.88F);
				position.setLowerLeftY(593.35F - 4f);
				position.setUpperRightX(241F);
				position.setUpperRightY(593.35F + 14f);
				txtLink.setRectangle(position);
				txtLink.setBorderStyle(noBorder);
				
				PDAnnotationLink txtLink2 = new PDAnnotationLink();
				PDRectangle position2 = new PDRectangle();
				position2.setLowerLeftX(35.88F);
				position2.setLowerLeftY(579.35F - 4f);
				position2.setUpperRightX(205.0F);
				position2.setUpperRightY(579.35F + 14f);
				txtLink2.setRectangle(position2);
				txtLink2.setBorderStyle(noBorder);
				
				PDAnnotationLink txtLink3 = new PDAnnotationLink();
				PDRectangle position3 = new PDRectangle();
				position3.setLowerLeftX(55.88F);
				position3.setLowerLeftY(565.35F - 4f);
				position3.setUpperRightX(155.0F);
				position3.setUpperRightY(565.35F + 14f);
				txtLink3.setRectangle(position3);
				txtLink3.setBorderStyle(noBorder);
				
				//TODO 315.4902F, 431.37F
				PDAnnotationLink txtLink4 = new PDAnnotationLink();
				PDRectangle position4 = new PDRectangle();
				position4.setLowerLeftX(425.4902F);
				position4.setLowerLeftY(431.37F - 3f);
				position4.setUpperRightX(540.4902F);
				position4.setUpperRightY(431.37F + 9f);
				txtLink4.setRectangle(position4);
				txtLink4.setBorderStyle(noBorder);
				
				PDActionURI action = new PDActionURI();
				action.setURI("https://www.ibm.com/account/reg/us-en/signup?formid=urx-35868");
				txtLink.setAction(action);
				txtLink2.setAction(action);
				
				PDActionURI action2 = new PDActionURI();
				action2.setURI("https://www.ibm.com/ibmspm");
				txtLink3.setAction(action2);
				
				PDActionURI action3 = new PDActionURI();
				action3.setURI("https://www.ibm.com/legal/copytrade");
				txtLink4.setAction(action3);
				
				page6.getAnnotations().add(txtLink);
				page6.getAnnotations().add(txtLink2);
				page6.getAnnotations().add(txtLink3);
				page6.getAnnotations().add(txtLink4);
			}
			
			// Saving the document
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        try {
	            doc.save(out);
	            doc.close();
	        } catch (Exception ex) {logger.log(Level.SEVERE, null, ex);}            
	        return out.toByteArray();
		}
	}
}
