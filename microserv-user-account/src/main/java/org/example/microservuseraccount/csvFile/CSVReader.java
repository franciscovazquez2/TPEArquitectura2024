package org.example.microservuseraccount.csvFile;

import jakarta.transaction.Transactional;
import org.example.microservuseraccount.dto.UserCreateDTO;
import org.example.microservuseraccount.dto.UserDto;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.entity.Authority;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.repository.AccountRepository;
import org.example.microservuseraccount.repository.AuthorityRepository;
import org.example.microservuseraccount.repository.UserRepository;
import org.example.microservuseraccount.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class CSVReader {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    private UserService userService;

    private static final String PATH = "microserv-user-account/src/main/resources/";
    private static final String CSVSPLIT = ",";

    @Transactional
    public void loadData(){
        readFileUser();
        readFileAccount();
        readFileAuthority();
        readFileAccountUserRelation();
        //readFileUserAuthorityRelation();
    }

    //lee archivos y los agrega a la base
    private void readFileUser() {
        String csvFile = PATH+"user.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    Optional<Authority> authorityOptional = authorityRepository.findById((datos[6]));
                    if (authorityOptional.isPresent()) {
                        UserCreateDTO user = new UserCreateDTO((datos[0]), (datos[1]), (datos[2]), (datos[3]), (datos[4]), (datos[5]), authorityOptional.get());
                        userService.createUser(user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileAccount() {
        String csvFile = PATH+"account.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    Account account = new Account(Long.parseLong(datos[0]), new Date(Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),Integer.parseInt(datos[3])),Double.parseDouble(datos[4]),Boolean.parseBoolean(datos[5]));
                    accountRepository.save(account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileAuthority() {
        String csvFile = PATH+"authority.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    Authority authority = new Authority((datos[0]));
                    authorityRepository.save(authority);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileAccountUserRelation(){
        String csvFile = PATH+"account-user.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    User user = userRepository.findById(Long.parseLong(datos[0])).orElse(null);
                    Account account = accountRepository.findById(Long.parseLong(datos[1])).orElse(null);
                    if(user!=null && account !=null){
                    user.addAccount(account);
                    account.addUser(user);
                    userRepository.save(user);
                    }else{
                        System.out.print(user+""+account +" nulos");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFileUserAuthorityRelation(){
        String csvFile = PATH+"account-authority.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    User user = userRepository.findById(Long.parseLong(datos[0])).orElse(null);
                    Authority authority = authorityRepository.findById((datos[1])).orElse(null);
                    if(user!=null && authority !=null){
                        user.addRol(authority);
                        userRepository.save(user);
                    }else{
                        System.out.print(user+""+authority +" nulos");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


