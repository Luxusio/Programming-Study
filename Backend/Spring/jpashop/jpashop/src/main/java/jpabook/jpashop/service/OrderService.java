package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.order.OrderRepository;
import jpabook.jpashop.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * order
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // You can choose
        // Domain Model Pattern : http://martinfowler.com/eaaCatalog/domainModel.html
        // Transaction Script pattern : http://martinfowler.com/eaaCatalog/transactionScript.html
        // in this case, we used domain model pattern

        // lookup entity
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // create delivery
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // create order item
        OrderItem orderItem = OrderItem.createOrderItem(item, count * item.getPrice(), count);

        // create order
        Order order = Order.createOrder(member, delivery, orderItem);

        // save order
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * cancel order
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        // cancel order
        order.cancel();
    }

    /**
     * search
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
