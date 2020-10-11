package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("qr")
@AllArgsConstructor
public class LoginController {

    QrService qrService;
    private final ReactiveRedisOperations<String, QrSession> qrSessionOps;

    @GetMapping("all")
    public Flux<QrSession> all(){
        return qrSessionOps.keys("*")
                .flatMap(qrSessionOps.opsForValue()::get);
    }

    @GetMapping("listen/{qr}")
    public Mono<QrSession> listen(@PathVariable String qr){
        return qrService.pop(qr);
    }

    @PutMapping("put")
    public Mono<Long> put(@RequestBody /*MultiValueMap<String, String> formData*/ QrSession qrSession){
        System.out.println(qrSession);
        return qrService.put(qrSession);
    }



    @GetMapping("/generate")
    public Mono<String> getQR() {
        return qrService.generateQR();
    }

    @GetMapping("/getput")
    public Mono<String> getPut() {
        return qrService.getQrAndPersist();
    }



    @GetMapping("/authentication")
    public Mono<String> getAuthenticationData() {
        return qrService.generateQR();
    }





}
