package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Sale> sales = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			
			while (line != null) {
				String[] fields = line.split(",");
				
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String name = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				
				sales.add(new Sale(month, year, name, items, total));
				
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			
			Set<String> names = new HashSet<>();
			
			names = sales.stream()
					.map(s -> s.getSeller())
					.collect(Collectors.toSet());
			
			for (String name : names) {
				double sum = sales.stream()
						.filter(s -> s.getSeller().equals(name))
						.map(s -> s.getTotal())
						.reduce(0.0, (s1, s2) -> s1 + s2);
				
				System.out.println(name + " - R$ " + String.format("%.2f", sum));
			}			
		}
		catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		
		sc.close();
	}

}
