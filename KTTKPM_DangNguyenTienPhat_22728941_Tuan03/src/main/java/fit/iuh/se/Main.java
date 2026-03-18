package fit.iuh.se;

import fit.iuh.se.tuan02.bai2.bai2_1.decorator.BaseOrder;
import fit.iuh.se.tuan02.bai2.bai2_1.decorator.DiscountDecorator;
import fit.iuh.se.tuan02.bai2.bai2_1.decorator.GiftWrapDecorator;
import fit.iuh.se.tuan02.bai2.bai2_1.decorator.Order;

/**
 * @author : user664dntp
 * @mailto : phatdang19052004@gmail.com
 * @created : 18/03/2026, Wednesday
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Kịch bản 1: Mua đơn hàng bình thường ---");
        Order myOrder = new BaseOrder(100000); // Đơn gốc 100k
        System.out.println("Chi tiết: " + myOrder.getDescription());
        System.out.println("Tổng tiền: " + myOrder.getCost() + " VND\n");

        System.out.println("--- Kịch bản 2: Đơn hàng mang đi tặng (Thêm gói quà) ---");
        Order giftOrder = new BaseOrder(100000);
        giftOrder = new GiftWrapDecorator(giftOrder); // Bọc lớp thứ 1
        System.out.println("Chi tiết: " + giftOrder.getDescription());
        System.out.println("Tổng tiền: " + giftOrder.getCost() + " VND\n");

        System.out.println("--- Kịch bản 3: Đơn tặng + Có mã giảm giá (Bọc nhiều lớp) ---");
        Order comboOrder = new BaseOrder(100000);
        comboOrder = new GiftWrapDecorator(comboOrder);     // Bọc lớp 1: Gói quà
        comboOrder = new DiscountDecorator(comboOrder);     // Bọc lớp 2: Giảm giá

        System.out.println("Chi tiết: " + comboOrder.getDescription());
        System.out.println("Tổng tiền: " + comboOrder.getCost() + " VND");
        // Giải thích: 100k (gốc) + 50k (gói quà) - 20k (giảm giá) = 130k
    }
}