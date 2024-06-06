package org.marv.controller;

import org.marv.requests.CreateAccRequest;
import org.marv.responses.Response;
import org.marv.responses.AccountDTO;
import org.marv.responses.QRCodeDTO;
import org.marv.services.AccountService;
import org.marv.services.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ResponseBody
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private AccountService accountService;

   @PostMapping(value="/create",
            produces={"application/json"})
    public ResponseEntity<?> createAccount(@RequestBody CreateAccRequest request) {
    try {
        AccountDTO accountDTO = accountService.generateAccount(request.getName(), request.getId());
        return ResponseEntity.ok().body(accountDTO);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error in generating Account: " + e.getMessage());
    }
    }
   @GetMapping(value="/{id}/deactivate",
            produces={"application/json"})
    public ResponseEntity<?> deactivateAccount(@PathVariable String id) {
        AccountDTO accountDTO = accountService.deactivateAccount(id);
        return ResponseEntity.ok(accountDTO);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new Response());
    }

   @GetMapping(value="/{accountId}/qrcodes",
            produces={"application/json"})
    public ResponseEntity<List<QRCodeDTO>> getQRCodesByAccount(@PathVariable String accountId) {
        try {
            List<QRCodeDTO> qrCodeDTOS = qrCodeService.getQRCodesByAccountId(accountId).stream()
                    .filter(Objects::nonNull)
                    .filter(QRCodeDTO::isDeactivated2)
                    .collect(Collectors.toList());
            if (qrCodeDTOS.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(qrCodeDTOS);
        } catch (Exception e) {
            System.out.println("Error fetching QR codes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping(value = "/{accountId}/allqrcodes")
    public ResponseEntity<List<QRCodeDTO>> getAllQRCodesByAccount(@PathVariable String accountId) {
        try {
            List<QRCodeDTO> qrCodeDTOS = qrCodeService.getQRCodesByAccountId(accountId).stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (qrCodeDTOS.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(qrCodeDTOS);
        } catch (Exception e) {
            System.out.println("Error fetching QR codes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
