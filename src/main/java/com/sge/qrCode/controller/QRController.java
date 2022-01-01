package com.sge.qrCode.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import io.nayuki.qrcodegen.QrCode;

@Controller
public class QRController {
	
	@RequestMapping("/")
	public String qr() {
		System.out.println("QRController::home()");
		return "qr.jsp";
	}

	
	@RequestMapping("/displayqr")
	public ModelAndView displayqr(@RequestParam String qrText) {
		System.out.println("QRController::displayQRCode()");
		System.out.println("qr text: " + qrText);
		
		// String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		// System.out.println("sessionId: " + sessionId);
		
		ModelAndView mv = new ModelAndView("displayQR.jsp");
		mv.addObject("qrtext", qrText);
		
		QrCode qr = QrCode.encodeText(qrText, QrCode.Ecc.QUARTILE);
		String svg = ImageOperations.toSvgString(qr, 4, "#FFFFFF", "#000000");  
		mv.addObject("svg", svg);
		
		
		return mv;
	}

	
	@RequestMapping(value = "imageqrcode/{qrtext}")
	@ResponseBody
	public ResponseEntity<byte[]> imageqrcode(@PathVariable String qrtext, HttpServletRequest request) {
		System.out.println("QRController::imageqrcode() " + qrtext);

		BufferedImage bufferedImage = ImageOperations.toImage(QrCode.encodeText(qrtext, QrCode.Ecc.QUARTILE), 20, 4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			System.out.println("QRController::imageqrcode() IOException");
			e.printStackTrace();
		}
		byte[] imageBytes = baos.toByteArray();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}
	
	
	/*
	public ResponseEntity<byte[]> imageqrcode(@PathVariable String qrtext, HttpServletRequest request) {
		System.out.println("QRController::imageqrcode() " + qrtext);

		BufferedImage bufferedImage = ImageOperations.toImage(QrCode.encodeText(qrtext, QrCode.Ecc.QUARTILE), 20, 4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			System.out.println("QRController::imageqrcode() IOException");
			e.printStackTrace();
		}
		byte[] imageBytes = baos.toByteArray();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}
	*/
    
	
	/*
	@RequestMapping(value = "imageqrcode/{qrtext}")
	@ResponseBody
	public ResponseEntity<byte[]> imageqrcode(@PathVariable String qrtext, HttpServletRequest request) {
		System.out.println("QRController::imageqrcode() " + qrtext);

		BufferedImage bufferedImage = ImageOperations.toImage(QrCode.encodeText(qrtext, QrCode.Ecc.QUARTILE), 20, 4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			System.out.println("QRController::imageqrcode() IOException");
			e.printStackTrace();
		}
		byte[] imageBytes = baos.toByteArray();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}
	*/	
    
	
	@GetMapping("image")
	public ResponseEntity<byte[]> image() {
		System.out.println("QRController::image()");

		ClassPathResource imageFile = new ClassPathResource("qrcode.png");

		byte[] imageBytes;
		try {
			imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
		} catch (IOException e) {
			System.out.println("QRController::ResponseEntity() IOException");
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = "downloadsvg/{qrtext}")
	public ResponseEntity<String> downloadsvg(@PathVariable String qrtext)  {
		System.out.println("QRController::downloadsvg()");

		QrCode qr = QrCode.encodeText(qrtext, QrCode.Ecc.QUARTILE);
		String svg = ImageOperations.toSvgString(qr, 4, "#FFFFFF", "#000000");  
		
		return ResponseEntity.ok().contentType(MediaType.TEXT_XML).body(svg);
	}
	
	
	public void createSvgFile(QrCode qr) {
		System.out.println("QRController::createSvgFile()");
		String svg = ImageOperations.toSvgString(qr, 4, "#FFFFFF", "#000000");  
		File svgFile = new File("hello-world-QR.svg");          
		try {
			Files.write(svgFile.toPath(), svg.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.out.println("QRController::createSvgFile() IOException");
			e.printStackTrace();
		}		
	}

	
	@GetMapping("imageqr")
	public ResponseEntity<byte[]> imageqr() {
		System.out.println("QRController::imageqr()");

		QrCode qr = QrCode.encodeText("textToQR", QrCode.Ecc.QUARTILE);
		BufferedImage bufferedImage = ImageOperations.toImage(qr, 20, 4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "png", baos);
		} catch (IOException e) {
			System.out.println("QRController::ResponseEntity() IOException");
			e.printStackTrace();
		}
		byte[] imageBytes = baos.toByteArray();
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	}

	@RequestMapping(value = "/getImage/{qrtext}")
	@ResponseBody
	public ResponseEntity<byte[]> getImage(@PathVariable String qrtext, HttpServletRequest request) {
	    try {
	        byte[] imageBytes = getImageBytes(request.getSession().getServletContext().getRealPath("/"), "qr", "qrcode", request, qrtext);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}	
	

	public byte[] getImageBytes(String path, String directoryName, String fileName, HttpServletRequest request, String qrtext) {
	    ByteArrayOutputStream byteArrayOutputStream;
	    BufferedImage bufferedImage;
	    byte[] imageInByte;
	    try {
	        byteArrayOutputStream = new ByteArrayOutputStream();
	        
	        bufferedImage = ImageOperations.toImage(QrCode.encodeText(qrtext, QrCode.Ecc.QUARTILE), 10, 4);
	        // bufferedImage = ImageIO.read(new File(path + directoryName + fileName));
	        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
	        byteArrayOutputStream.flush();
	        imageInByte = byteArrayOutputStream.toByteArray();
	        byteArrayOutputStream.close();
	        return imageInByte;
	    } catch (FileNotFoundException fnfe) {
	        fnfe.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
}
