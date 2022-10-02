package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		List<Sale> list = new ArrayList<>();

		System.out.println("Entre com o caminho do arquivo:");
		String path = sc.next();
		System.out.println();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] field = line.split(",");
				int month = Integer.parseInt(field[0]);
				int year = Integer.parseInt(field[1]);
				String seller = field[2];
				int items = Integer.parseInt(field[3]);
				double total = Double.parseDouble(field[4]);
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			List<String> names = list.stream()
					.map(p -> p.getSeller())
					.distinct()
					.collect(Collectors.toList());
			
			System.out.println("Total de vendas por vendedor:");
			
			names.forEach(string -> {
				System.out.println(string + " - R$ " +
						String.format("%.2f", list.stream()
								.filter(p -> p.getSeller().equals(string))
								.map(p -> p.getTotal())
								.reduce((double) 0, (x, y) -> x+y)));
			});
							
		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}

		sc.close();

	}

}
