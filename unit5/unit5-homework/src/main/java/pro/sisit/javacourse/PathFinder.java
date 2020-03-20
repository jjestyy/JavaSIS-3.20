package pro.sisit.javacourse;

import pro.sisit.javacourse.optimal.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class PathFinder {

    /**
     * Возвращает оптимальный транспорт для переданной задачи.
     * Если deliveryTask равна null, то оптимальеый транспорт тоже равен null.
     * Если список transports равен null, то оптимальеый транспорт тоже равен null.
     */
    public Transport getOptimalTransport(DeliveryTask deliveryTask, List<Transport> transports) {
        Map<RouteType, BigDecimal> routeLengthLink = deliveryTask.getRoutes().stream()
                .collect(Collectors.toMap(Route::getType, Route::getLength));

        Optional<Transport> optionalTransport = transports.stream()
                .filter(transport -> routeLengthLink.containsKey(transport.getType()))
                .filter(transport -> transport.getVolume().compareTo(deliveryTask.getVolume()) >= 0)
                .min(Comparator.comparing(
                transport ->
                        transport.getPrice().multiply(routeLengthLink.get(transport.getType()))));
        return optionalTransport.orElse(null);

    }
}