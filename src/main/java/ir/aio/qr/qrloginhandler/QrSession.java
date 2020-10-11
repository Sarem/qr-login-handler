package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QrSession {
    private String id;
    private String userId;
    private String password;
}
