package pro.sisit.javacourse;

import pro.sisit.javacourse.inverse.InverseDeliveryTask;
import pro.sisit.javacourse.inverse.Solution;
import pro.sisit.javacourse.optimal.DeliveryTask;
import pro.sisit.javacourse.optimal.Route;
import pro.sisit.javacourse.optimal.Transport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InversePathFinder {

    /**
     * Принимает на вход InverseDeliveryTask task - обратную задачу доставки груза.
     * Мы должны по переданному в ней списку возможных задач, списку доступного транспорта,
     * а также диапазону цены определить что за задачи доставки груза могли быть решены.
     * Возвращает список решений задачи доставки груза (Solution),
     * удовлетворяющий параметру переданному параметру task:
     * 1. Транспорт должен быть доступен для решения данной задачи
     * 2. Стоимость решения задачи должна входить в диапазон цены пераметра task
     * 3. Также возвращаемый список должен быть осортирован по двум значениям:
     * - сначала по итоговой стоимости решения задачи - по убыванию
     * - затем, если цены одинаковы - по наименованию задачи доставки по алфавиту - по возрастанию
     * Если task равна null, то функция должна вернуть пустой список доступных решений.
     * Если внутри параметра task данные по возможным задачам, доступному транспорту либо по итоговой цене равны null,
     * то функция должна вернуть пустой список доступных решений.
     */
    public List<Solution> getAllSolutions(InverseDeliveryTask task) {
        List<Solution> allSolutions = new ArrayList<>();
        if (task == null || task.getTransports() == null || task.getTasks() == null || task.getPriceRange() == null) return  allSolutions;
         task.getTasks().forEach(
                 deliveryTask -> task.getTransports().forEach(
                         transport -> {
                             if(deliveryTask.getRoutes().stream().anyMatch(route -> route.getType() == transport.getType())) {
                                 allSolutions.add(new Solution(deliveryTask, transport, getCost(deliveryTask, transport) )); }}
                         ));
        return allSolutions.stream()
                .filter(solution -> solution.getPrice() != null)
                .filter(solution -> task.getPriceRange().isInRange(solution.getPrice()))
                .filter(solution -> solution.getTransport().getVolume().compareTo(solution.getDeliveryTask().getVolume()) >= 0)
                .sorted(Comparator.comparing(Solution::getPrice).reversed().thenComparing(solution -> solution.getDeliveryTask().getName()))
                .collect(Collectors.toList());
    }

    private BigDecimal getCost (DeliveryTask deliveryTask, Transport transport) {
        Optional<Route> requiredRoute = deliveryTask.getRoutes().stream().filter(route -> route.getType() == transport.getType()).findFirst();
        return requiredRoute.map(route -> transport.getPrice().multiply(route.getLength()) ).orElse(null);
    }
}
