package org.marv.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.transaction.Transactional;
import org.marv.entities.ShortUrl;
import org.marv.repository.AccountRepository;
import org.marv.repository.QRCodeRepository;
import org.marv.repository.ShortUrlRepository;
import org.marv.entities.Account;
import org.marv.entities.QRCode;
import org.marv.requests.QRCodeRequest;
import org.marv.responses.QRCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.marv.requests.UpdateQRCodeRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QRCodeService {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    public QRCode generateAndSaveQRCode(QRCodeRequest request, int width, int height, String directory, String accountId, String shortcode) throws Exception {
        Optional<Account> accOpt = accountRepository.findById(accountId);
        if (accOpt.isPresent()) {
            Account acc = accOpt.get();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode("https://uni.grub-bros.de:8081/api/url/" + shortcode, BarcodeFormat.QR_CODE, width, height);
            // Path path = Paths.get(directory, "QRCode_" + System.currentTimeMillis() + ".svg");

            //MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" standalone=\"no\"?>");
            sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
            sb.append("<svg width=\"" + width + "\" height=\"" + height + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");

            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    if (bitMatrix.get(x, y)) {
                        sb.append("<rect x=\"" + x + "\" y=\"" + y + "\" width=\"1\" height=\"1\" fill=\"#000000\"/>");
                    }
                }
            }

            sb.append("</svg>");
            String p = "QRCode_" + System.currentTimeMillis() + ".svg";
            Path path = Paths.get(directory, p);
            try {
                Files.write(path, sb.toString().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
            QRCode qrCode = new QRCode();
            qrCode.setAccount(acc);
            qrCode.setUrl(request.getUrl());
            qrCode.setFilePath("../../assets/qr/" + p);
            qrCode.setImg(request.getImg());
            qrCode.setText(request.getText());
            qrCode.setPermalink(request.isPermalink());
            qrCode.setPrimaryColor(request.getPrimaryColor());
            qrCode.setTextColor(request.getTextColor());
            qrCode.setActive(true);

            qrCodeRepository.save(qrCode);

            acc.addQRCode(qrCode);

            return qrCode;
        } else {
            throw new RuntimeException("Account not found with id: " + accountId);
        }
    }

    @Transactional
    public void deactivateQRCode(Long qrCodeId) {
        Optional<QRCode> qr = qrCodeRepository.findById(qrCodeId);
        if(qr.isPresent()) {
            QRCode qrCode = qr.get();
            if(!qrCode.isActive()) {
                qrCodeRepository.activateQRCode(qrCodeId);
            } else {
                qrCodeRepository.deactivateQRCode(qrCodeId);
            }
        }

    }

    @Transactional
    public void deleteQRCode(Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new IllegalStateException("QR Code not found"));
        if(!qrCode.isPermalink()) {
            urlShortenerService.deleteShortUrl(qrCode.getShortUrl().getId());
        }
        qrCodeRepository.deleteById(qrCodeId);
    }


    public QRCodeDTO getQRCodePara(Long qrCodeId) {
        QRCode qrCode = qrCodeRepository.findById(qrCodeId)
                .orElseThrow(() -> new IllegalStateException("QR Code not found"));
        ShortUrl shortUrl = qrCode.getShortUrl();
        QRCodeDTO qrCodeDTO = new QRCodeDTO(
                qrCode.getId(),
                qrCode.getImg(),
                qrCode.getText(),
                qrCode.getUrl(),
                shortUrl != null ? shortUrl.getShortCode() : null,
                qrCode.getFilePath(),
                qrCode.getPrimaryColor(),
                qrCode.getTextColor(),
                qrCode.isActive(),
                qrCode.getAccount() != null ? qrCode.getAccount().getId() : null,
                qrCode.isPermalink(),
                qrCode.getShortUrl() != null ? qrCode.getShortUrl().getId() : null
        );

        return qrCodeDTO;
    }
    public QRCodeDTO getQRCodeDTO(QRCode qrCode, ShortUrl shortUrl) {
        QRCodeDTO qrCodeDTO = new QRCodeDTO(
                qrCode.getId(),
                qrCode.getImg(),
                qrCode.getText(),
                qrCode.getUrl(),
                shortUrl != null ? shortUrl.getShortCode() : null,
                qrCode.getFilePath(),
                qrCode.getPrimaryColor(),
                qrCode.getTextColor(),
                qrCode.isActive(),
                qrCode.getAccount() != null ? qrCode.getAccount().getId() : null,
                qrCode.isPermalink(),
                qrCode.getShortUrl() != null ? qrCode.getShortUrl().getId() : null
        );

        return qrCodeDTO;
    }


    public List<QRCodeDTO> getQRCodesByAccountId(String accountId) {
        List<QRCode> qrCodes = qrCodeRepository.findByAccount_Id(accountId);
        if (qrCodes == null) {
            System.out.println("No QR codes found for account ID: " + accountId);
            return Collections.emptyList();
        }
        System.out.println("Number of QR Codes found for account ID " + accountId + ": " + qrCodes.size());

        return qrCodes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public QRCodeDTO convertToDTO(QRCode qrCode) {
        Long shortUrlId = null;
        String shortedUrl = null;

        if (qrCode.getShortUrl() != null) {
            shortUrlId = qrCode.getShortUrl().getId();
            shortedUrl = qrCode.getShortUrl().getShortCode();
        }
        String accountId = (qrCode.getAccount() != null) ? qrCode.getAccount().getId() : null;

        System.out.print(qrCode.getShortUrl().getId());


        return new QRCodeDTO(
                qrCode.getId(),
                qrCode.getImg(),
                qrCode.getText(),
                qrCode.getUrl(),
                shortedUrl,
                qrCode.getFilePath(),
                qrCode.getPrimaryColor(),
                qrCode.getTextColor(),
                qrCode.isActive(),
                accountId,
                qrCode.isPermalink(),
                shortUrlId
        );
    }
    public QRCodeDTO updateQRCode(UpdateQRCodeRequest updateRequest) {
        QRCode qrCode = qrCodeRepository.findById(updateRequest.getId())
                .orElseThrow(() -> new IllegalStateException("QR Code not found"));

        qrCode.setText(updateRequest.getText());
        qrCode.setTextColor(updateRequest.getTextColor());
        qrCode.setPrimaryColor(updateRequest.getPrimaryColor());
        qrCode.setImg(updateRequest.getImg());
        qrCode.setActive(updateRequest.isIsactive());
        qrCode.setTextColor(updateRequest.getTextColor());
        qrCodeRepository.save(qrCode);


        return getQRCodeDTO(qrCode, qrCode.getShortUrl());
    }
    /*List<Object[]> qrs = qrcodes.stream().map(qrcode -> new QRCodeDTO(
                   qrcode.getId(),
                   qrcode.getImg(),
                   qrcode.getText(),
                   qrcode.getUrl(),
                   qrcode.getShortUrl() != null ? qrcode.getShortUrl().getOriginalUrl() : null,
                   qrcode.getFilePath(),
                   qrcode.getPrimaryColor(),
                   qrcode.getTextColor(),
                   qrcode.isActive(),
                   qrcode.getAccount() != null ? qrcode.getAccount().getId() : null,
                   qrcode.isPermalink(),
                   qrcode.getShortUrl() != null ? qrcode.getShortUrl().getId() : null
           )).collect(Collectors.toList());
           System.out.println(qrs.isEmpty());
           for (QRCodeDTO dto : qrs) {
               System.out.println("QR Code ID: " + dto.getId());
           }

           return  qrs;*/
    public Optional<QRCode> getQRCode(Long qrCodeId) {
        return qrCodeRepository.findById(qrCodeId);
    }
}