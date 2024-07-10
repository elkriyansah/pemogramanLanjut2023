import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input jumlah kolom: ");
        int kolom = scanner.nextInt();

        System.out.print("Input jumlah baris: ");
        int baris = scanner.nextInt();

        int[][] matriks = new int[baris][kolom];

        // Input nilai untuk matriks
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                System.out.print("Input nilai baris " + (i+1) + ", kolom " + (j+1) + " = ");
                matriks[i][j] = scanner.nextInt();
            }
        }

        // Cetak matriks asli
        System.out.println("Matriks asli:");
        cetakMatriks(matriks);

        // Hitung dan cetak matriks transpose
        int[][] transpose = transposeMatriks(matriks);
        System.out.println("Matriks transpose:");
        cetakMatriks(transpose);

        // Hitung dan cetak hasil penjumlahan matriks asli dengan transpose-nya
        int[][] hasilJumlah = jumlahkanMatriks(matriks, transpose);
        System.out.println("Hasil penjumlahan matriks asli dengan transpose-nya:");
        cetakMatriks(hasilJumlah);
    }

    public static int[][] transposeMatriks(int[][] matriks) {
        int baris = matriks.length;
        int kolom = matriks[0].length;
        int[][] transpose = new int[kolom][baris];

        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                transpose[j][i] = matriks[i][j];
            }
        }

        return transpose;
    }

    public static int[][] jumlahkanMatriks(int[][] matriks1, int[][] matriks2) {
        int baris = matriks1.length;
        int kolom = matriks1[0].length;
        int[][] hasil = new int[baris][kolom];

        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                hasil[i][j] = matriks1[i][j] + matriks2[i][j];
            }
        }

        return hasil;
    }

    public static void cetakMatriks(int[][] matriks) {
        for (int i = 0; i < matriks.length; i++) {
            for (int j = 0; j < matriks[i].length; j++) {
                System.out.print(matriks[i][j] + " ");
            }
            System.out.println();
        }
    }
}