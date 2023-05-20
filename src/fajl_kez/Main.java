package fajl_kez;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    private Adatok[] adatok;

    public static void main(String[] args) throws IOException {
        new Main().feladatok();
    }

    public Main() throws IOException {
        List<String> sorok = Files.readAllLines(Path.of("fajl.csv"));
        assert !sorok.isEmpty() : "üres a fájl";

        adatok = new Adatok[sorok.size() - 1];
        for (int i = 0; i < sorok.size() - 1; i++) {
            String sor = sorok.get(i + 1);
            Adatok adat = new Adatok(sor);
            adatok[i] = adat;
        }
    }

    private void feladatok() throws IOException {
        feladat1();
        feladat2();
        feladat3();
        feladat4();
        feladat5();
        feladat6();
        feladat7();
    }

    private void feladat1() {
        System.out.println("1. feladat: Ki keresi a legtöbbet?");
        int index = 0;
        for (int i = 1; i < adatok.length; i++) {
            if (adatok[i].getFizetes() > adatok[index].getFizetes()) {
                index = i;
            }
        }
        System.out.printf("%s keresi a legtöbbet! \n", adatok[index].getNev());
        String nev = adatok[index].getNev();
        assert nev.equals("Xénia") : "1. hibás válasz";
        System.out.println("------");
    }

    private void feladat2() {
        System.out.println("2. feladat: Mennyi az átlag fizetés?");
        int osszeg = 0;
        for (int i = 0; i < adatok.length; i++) {
            osszeg += adatok[i].getFizetes();
        }
        System.out.printf("Az átlag fizetés %dFt !\n", osszeg / adatok.length);
        double atlag = osszeg / adatok.length;
        assert atlag == 501_800 : "2. hibás válasz";
        System.out.println("------");
    }

    private void feladat3() {
        System.out.println("3. feladat: Mindenki budapesti?");
        int i = 0;
        while (i < adatok.length && adatok[i].getCim().equals("Budapest")) {
            i++;
        }
        System.out.println(i >= adatok.length ? "igen" : "nem");
        System.out.println("------");
        boolean mind = i >=adatok.length ? true : false;
        assert mind == false: "3. hibás válasz";
        assert i <= 1 : "3. hibás progTétel"; 
    }

    private void feladat4() {
        System.out.println("4. feladat: Van 20 év feletti budapesti?");
        int i = 0;
        while (i < adatok.length && !((adatok[i].getCim().equals("Budapest")) && (adatok[i].getKor() > 20))) {
            i++;
        }
        System.out.println(i < adatok.length ? "igen" : "nem");
        System.out.println("------");
        boolean van = i < adatok.length;
        assert van == true: "4. hibás válasz";
        assert i <= 1 : "4. hibás progTétel"; 
    }

    private void feladat5() {
        System.out.println("5. feladat: milyen címek vannak eltárolva?");
        Set<String> cimek = new HashSet<>();
        for (Adatok adat : adatok) {
            cimek.add(adat.getCim());
        }
        for (String cim : cimek) {
            System.out.println(cim);
        }
        System.out.println("------");
    }

    private void feladat6() {
        System.out.println("6. feladat: milyen címen hányan laknak?");
        Map<String, Integer> lakcimek = new HashMap<>();
        for (Adatok adat : adatok) {
            String kulcs = adat.getCim();
            if (lakcimek.containsKey(kulcs)) {
                int ertek = lakcimek.get(kulcs);
                lakcimek.put(kulcs, ++ertek);

            } else {
                lakcimek.put(kulcs, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : lakcimek.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.printf("%-12s: %4d db\n", key, value);
        }
        System.out.println("------");
    }

    private void feladat7() throws IOException {
        System.out.println("7. feladat: Írd ki a 'nemBp.txt' fájlba fejléccel a nem budapestiek minden adatát!");
        List<Adatok> bpAdatok = new ArrayList<>();
        for (Adatok adat : adatok) {
            if (!adat.getCim().equals("Budapest")) {
                bpAdatok.add(adat);
            }
        }
        String kimenet = "";
        for (Adatok adat : bpAdatok) {
            kimenet += adat + "\n";
        }
        Files.writeString(Path.of("nemBp.txt"), kimenet);
        System.out.println("Fájlba kiírás megtörtént!");
        System.out.println("------");
    }

}
