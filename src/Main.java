import com.dao.*;
import com.models.*;
import java.sql.SQLException;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        MenuDAO menuDAO = new MenuDAO();
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();

        try {
            System.out.println("===== Welcome to Online Ordering System =====");
            sc.nextLine(); 
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            System.out.print("Enter true/false if you are admin or not: ");
            boolean isAdmin = sc.nextBoolean();

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPasswordHash(password);
            user.setAdmin(isAdmin);

            int userId = userDAO.create(user);   
            user.setId(userId);                  

            System.out.println("\nWelcome, " + user.getName());
            System.out.println("Your User ID: " + user.getId());

            
            while (true) {
                System.out.println("\n--- MAIN MENU ---");
                System.out.println("1. View Menu");
                System.out.println("2. Add Item to Cart");
                System.out.println("3. View Cart");
                System.out.println("4. Place Order");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        viewMenu(menuDAO);
                        break;

                    case 2:
                        addToCart(cartDAO, user.getId());
                        break;

                    case 3:
                        viewCart(cartDAO, user.getId());
                        break;

                    case 4:
                        placeOrder(cartDAO, orderDAO, user.getId());
                        break;

                    case 5:
                        System.out.println("Thank you for using the system.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- VIEW MENU ----------
    private static void viewMenu(MenuDAO menuDAO) throws SQLException {
        List<MenuItem> menu = menuDAO.listAll();

        System.out.println("\n--- MENU ---");
        for (MenuItem item : menu) {
            System.out.println(
                    item.getId() + ". " +
                    item.getName() + " - ₹" +
                    item.getPrice()
            );
        }
    }

    
    private static void addToCart(CartDAO cartDAO, int userId) throws SQLException {
        System.out.print("Enter Menu Item ID: ");
        int menuId = sc.nextInt();

        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();

        cartDAO.addOrUpdate(userId, menuId, qty);
        System.out.println("Item added/updated in cart.");
    }

   
    private static void viewCart(CartDAO cartDAO, int userId) throws SQLException {
        List<CartItem> cart = cartDAO.list(userId);

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("\n--- YOUR CART ---");
        for (CartItem ci : cart) {
            System.out.println(
                    ci.getMenuName() +
                    " | Qty: " + ci.getQuantity() +
                    " | Price: ₹" + ci.getPrice() +
                    " | Line Total: ₹" + ci.getLineTotal()
            );
            total += ci.getLineTotal();
        }
        System.out.println("Cart Total: ₹" + total);
    }

    
    private static void placeOrder(CartDAO cartDAO, OrderDAO orderDAO, int userId) throws SQLException {
        List<CartItem> cart = cartDAO.list(userId);

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot place order.");
            return;
        }

        sc.nextLine(); // consume newline

        System.out.print("Enter Delivery Address: ");
        String address = sc.nextLine();

        System.out.print("Payment Method (COD / CARD / UPI): ");
        String payment = sc.nextLine();

        double total = 0;
        for (CartItem ci : cart) {
            total += ci.getLineTotal();
        }

        int orderId = orderDAO.createOrder(userId, total, address, payment);

        if (orderId > 0) {
            for (CartItem ci : cart) {
                orderDAO.addOrderItem(
                        orderId,
                        ci.getMenuId(),
                        ci.getQuantity(),
                        ci.getPrice()
                );
            }

            cartDAO.clear(userId);
            System.out.println("Order placed successfully.");
            System.out.println("Order ID: " + orderId);
        } else {
            System.out.println("Order failed.");
        }
    }
}
