package fit.iuh.se;

import fit.iuh.se.tuan02.bai2.bai2_1.state.OrderContext;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class Main {
    public static void main(String[] args) {
        // Kịch bản 1: Đơn hàng giao thành công (Luồng chuẩn - Happy Path)
        OrderContext order1 = new OrderContext();
        order1.verifyOrder();
        order1.processOrder();
        order1.deliverOrder();

        System.out.println("\n-----------------------------------\n");

        // Kịch bản 2: Đơn hàng bị hủy giữa chừng
        OrderContext order2 = new OrderContext();
        order2.verifyOrder();
        order2.cancelOrder();
        order2.processOrder();

        System.out.println("\n-----------------------------------\n");

        // Kịch bản 3: Thao tác sai quy trình
        OrderContext order3 = new OrderContext();
        order3.processOrder();
    }
}