package ir.aio.qr.qrloginhandler;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QrService {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, QrSession> qrSessionOps;


    public Mono<String> generateQR() {
        return Mono.fromSupplier(() -> UUID.randomUUID().toString());
    }
    public Mono<Long> put(QrSession qrSession){
        return qrSessionOps.opsForList().leftPush(qrSession.getId(),qrSession);
    }
    public Mono<QrSession> pop(String s){
        return qrSessionOps.opsForList().leftPop(s, Duration.ofSeconds(100));
    }
}
