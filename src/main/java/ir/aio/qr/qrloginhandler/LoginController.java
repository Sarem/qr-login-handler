package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("qr")
@AllArgsConstructor
@ControllerAdvice
public class LoginController {

    private QrService qrService;


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
    public Mono<QrDTO> getQR() {
        return qrService.generateQR().map(s -> new QrDTO(s,"lo"));
    }


}
