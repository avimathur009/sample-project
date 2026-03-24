package com.example.demo.service.orderManagement.invoice;

import com.example.demo.entity.Invoice;
import com.example.demo.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public Invoice generateInvoice(BigDecimal totalAmount) {
        Invoice invoice = new Invoice();
        invoice.setTotalAmount(totalAmount);
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }
}
