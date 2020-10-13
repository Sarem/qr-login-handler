package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("qr")
@AllArgsConstructor
@ControllerAdvice
public class LoginController {

    QrService qrService;
    private final ReactiveRedisOperations<String, QrSession> qrSessionOps;

    @GetMapping("listen/{qr}")
    public Mono<QrSession> listen(@PathVariable String qr) {
        return qrService.pop(qr);
    }

    @PutMapping("put")
    public Mono<Long> put(@RequestBody QrSession qrSession) {
        return qrService.put(qrSession);
    }

    @GetMapping("/generate")
    public Mono<String> getQR() {
        return qrService.generateQR();
    }
}
