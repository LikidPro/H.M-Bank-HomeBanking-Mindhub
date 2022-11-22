package com.mindhub.homebanking.service.implement;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepositort;
import com.mindhub.homebanking.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;


@Service
public class PdfServiceImplement implements PdfService {

    @Autowired
    TransactionRepositort transactionRepositort;

    @Override
    public void export(
            Account account,
            Set<Transaction> transactionsFiltradas,
            HttpServletResponse response

    ) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        com.lowagie.text.Image image = null;
        image= Image.getInstance("src/main/resources/static/web/assets/Mi proyecto-1.png");
        image.scaleAbsolute(150,100);
        image.setAbsolutePosition(415,750);
        document.add(image);
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Transacciones",fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontSubTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(12);
        Paragraph space= new Paragraph("                                                                                                           ");

        Paragraph subtitle = new Paragraph("Transacciones de la cuenta  " + account.getNumber(),fontSubTitle);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(space);
        document.add(space);
        document.add(subtitle);

        document.add(space);
        document.add(space);


        PdfPTable table = new PdfPTable(4);
        Stream.of("Amount", "Description","Date","Type").forEach(table::addCell);

        transactionsFiltradas.forEach(transaction -> {
            table.addCell("$" +transaction.getAmonunt());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getDate());
            table.addCell(transaction.getType().toString());
        });

        document.add(table);


//        ArrayList<String> transactions = new ArrayList<>(transactionsFiltradas.stream().map(transaction1 -> transaction1.toString()).collect(Collectors.toList()));
//        for (int i=0;i < transactions.size(); i++){
//            String transaction = transactions.get(i);
//            Paragraph paragraph = new Paragraph(transaction,fontSubTitle);
//            document.add(paragraph);
//
//        }


        document.close();


        }
    }

