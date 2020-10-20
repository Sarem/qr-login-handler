package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrDTO {
    private String code;
    private String loginUrl;
}
