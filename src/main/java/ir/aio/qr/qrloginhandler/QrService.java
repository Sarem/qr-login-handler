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



    @PostConstruct
    public void loadData() {
        /*qrSessionOps.
        factory.getReactiveConnection().serverCommands().flushAll().thenMany(
                Flux.just("Jet Black Redis", "Darth Redis", "Black Alert Redis")
                        .map(name -> new QrSession(UUID.randomUUID().toString(), "name", ""))
                        .flatMap(qrSession -> qrSessionOps.opsForValue().set(qrSession.getId(), qrSession)))
                .thenMany(qrSessionOps.keys("*")
                        .flatMap(qrSessionOps.opsForValue()::get))
                .subscribe(System.out::println);*/
    }

    public Mono<String> generateQR() {
        return Mono.fromSupplier(() -> UUID.randomUUID().toString());
    }
    public Mono<Long> put(QrSession qrSession){
        return qrSessionOps.opsForList().leftPush(qrSession.getId(),qrSession);
    }
    public Mono<QrSession> pop(String s){
        return qrSessionOps.opsForList().leftPop(s, Duration.ofSeconds(100));
    }

    public Mono<String> getQrAndPersist() {

        String uuid=UUID.randomUUID().toString();
        return factory.getReactiveConnection().serverCommands().bgSave().then(
                Mono.just("Jet Black Redis")
                        .map(name -> new QrSession(uuid, "name", ""))
                        .flatMap(q -> qrSessionOps.opsForValue().set("uuid", q,Duration.ofSeconds(10)))
        )
                .then(
                        Mono.just(uuid));//subscribe(System.out::println);
    }

    public Mono<String> waitForQr(String qr) {

        String uuid=UUID.randomUUID().toString();
        return factory.getReactiveConnection().pubSubCommands().subscribe().then(
                Mono.just("Jet Black Redis")
                        .map(name -> new QrSession(uuid, "name", ""))
                        .flatMap(q -> qrSessionOps.opsForValue().set("uuid", q,Duration.ofSeconds(10)))
        )
                .then(
                        Mono.just(uuid));//subscribe(System.out::println);
    }

}
