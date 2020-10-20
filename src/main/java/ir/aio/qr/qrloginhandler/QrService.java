package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class QrService {

    private final ReactiveRedisOperations<String, QrSession> qrSessionOps;


    public Mono<String> generateQR() {
        return Mono.fromSupplier(() -> UUID.randomUUID().toString().substring(0,5));
    }

    public Mono<Long> put(QrSession qrSession) {
        return qrSessionOps.opsForList().leftPush(qrSession.getId(), qrSession);
    }

    public Mono<QrSession> pop(String s) {
        return qrSessionOps.opsForList().leftPop(s, Duration.ofSeconds(100));
    }
}
