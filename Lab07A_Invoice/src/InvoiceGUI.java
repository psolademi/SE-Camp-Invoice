import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGUI extends JFrame {
    private List<LineItem> lineItems;
    private JTextArea invoiceTextArea;
    private JTextField productNameField;
    private JTextField unitPriceField;
    private JTextField quantityField;

    public InvoiceGUI() {
        lineItems = new ArrayList<>();

        setTitle("Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        inputPanel.add(unitPriceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JButton addButton = new JButton("Add Line Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLineItem();
            }
        });
        inputPanel.add(addButton);

        JButton showInvoiceButton = new JButton("Show Invoice");
        showInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInvoice();
            }
        });
        inputPanel.add(showInvoiceButton);

        invoiceTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(invoiceTextArea);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setSize(400, 300);
        setVisible(true);
    }

    private void addLineItem() {
        String productName = productNameField.getText();
        double unitPrice = Double.parseDouble(unitPriceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());

        Product product = new Product(productName, unitPrice);
        LineItem lineItem = new LineItem(product, quantity);
        lineItems.add(lineItem);

        productNameField.setText("");
        unitPriceField.setText("");
        quantityField.setText("");
    }

    private void displayInvoice() {
        StringBuilder invoiceText = new StringBuilder();
        double totalAmount = 0.0;

        invoiceText.append("Invoice:\n\n");
        for (LineItem item : lineItems) {
            invoiceText.append(item.getProduct().getName()).append("\t")
                    .append(item.getQuantity()).append(" x ").append(item.getProduct().getUnitPrice())
                    .append(" = ").append(item.getTotal()).append("\n");
            totalAmount += item.getTotal();
        }
        invoiceText.append("\nTotal Amount Due: ").append(totalAmount);

        invoiceTextArea.setText(invoiceText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InvoiceGUI::new);
    }
}

class Product {
    private String name;
    private double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}

class LineItem {
    private Product product;
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return quantity * product.getUnitPrice();
    }
}
