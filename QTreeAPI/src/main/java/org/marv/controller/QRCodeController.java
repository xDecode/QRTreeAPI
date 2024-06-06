package org.marv.controller;

import org.marv.entities.ShortUrl;
import org.marv.requests.QRCodeRequest;
import org.marv.responses.Response;
import org.marv.requests.UpdateQRCodeRequest;
import org.marv.responses.QRCodeDTO;
import org.marv.services.QRCodeService;
import org.marv.entities.QRCode;
import org.marv.services.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@ResponseBody
@RestController
@RequestMapping("/api/qrcodes")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<?> createQRCode(@RequestBody QRCodeRequest request) {
        int width = 350;
        int height = 350;

        if(!request.isPermalink()) {
            try {
                ShortUrl shortUrl = urlShortenerService.createShortUrl(request.getUrl());
                QRCode qrCode = qrCodeService.generateAndSaveQRCode(request, width, height, "/var/www/html/assets/qr", request.getAccountId(), shortUrl.getShortCode());
                shortUrl.setQrcode(qrCode);
                shortUrl = urlShortenerService.saveShortUrl(shortUrl);
                qrCode.setShortUrl(shortUrl);
                QRCodeDTO dto = qrCodeService.getQRCodeDTO(qrCode, shortUrl);
                return ResponseEntity.ok().body(dto);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error in generating QR code: " + e.getMessage());
            }
        } else {
            try {
            QRCode qrCode = qrCodeService.generateAndSaveQRCode(request, width, height, "", request.getAccountId(), "nichts");
                return ResponseEntity.ok().body("QR Code generated and saved successfully with ID: " + qrCode.getId() + " and URL: " + request.getUrl());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error in generating QR code: " + e.getMessage());
            }
        }
    }
    @GetMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateQRCode(@PathVariable Long id) {
        try {
            qrCodeService.deactivateQRCode(id);
            return ResponseEntity.ok(new Response());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to deactivate QR Code: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteQRCode(@PathVariable Long id) {
        try {
            qrCodeService.deleteQRCode(id);
            return ResponseEntity.ok(new Response());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete QR Code: " + e.getMessage());
        }
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getQRCode(@PathVariable Long id) {
        try {
            QRCodeDTO qrCodeDTO = qrCodeService.getQRCodePara(id);
            return ResponseEntity.ok(qrCodeDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.GONE).body(e.getMessage());
        }
    }
    @PostMapping("/edit")
    public ResponseEntity<?> editQRCode(@RequestBody UpdateQRCodeRequest updateRequest) {
        try {
            QRCodeDTO updatedQRCode = qrCodeService.updateQRCode(updateRequest);
            return ResponseEntity.ok(new Response());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Failed to update QR Code: " + e.getMessage());
        }
    }

}
