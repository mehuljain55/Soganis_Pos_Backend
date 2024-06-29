package com.Soganis.Service;

import com.Soganis.Entity.Billing;
import com.Soganis.Entity.User;
import com.Soganis.Entity.UserCashCollection;
import com.Soganis.Entity.UserMonthlySalary;
import com.Soganis.Entity.UserMonthlySalaryId;
import com.Soganis.Entity.User_Salary;
import com.Soganis.Repository.BillingRepository;
import com.Soganis.Repository.ItemsRepository;
import com.Soganis.Repository.UserCashCollectionRepository;
import com.Soganis.Repository.UserMonthlySalaryRepository;
import org.springframework.stereotype.Service;
import com.Soganis.Repository.UserRepository;
import com.Soganis.Repository.UserSalaryRepository;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ItemsRepository itemRepo;

    @Autowired
    private UserSalaryRepository userSalaryRepo;

    @Autowired
    private BillingRepository billRepo;

    @Autowired
    private UserCashCollectionRepository userCashCollectionRepo;

    @Autowired
    private UserMonthlySalaryRepository userMonthlySalaryRepository;

    public User getUserInfo(String userId) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isPresent()) {
            User user = opt.get();
            return user;
        }
        return null;
    }

    public List<User> getUserList() {
        List<User> lst = userRepo.findAll();
        return lst;

    }

    public String userSalaryUpdate(List<User_Salary> salary) {
        try {
            userSalaryRepo.saveAll(salary);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }

    public int salaryDeduction(String userId, String type, int hours) {

        Optional<User> opt = userRepo.findById(userId);
        User user = new User();
        if (opt.isPresent()) {
            user = opt.get();
        }

        int amount = 0;
        int yearly_salary = user.getMonthly_salary() * 12;
        int per_day_salary = yearly_salary / 365;

        if (type.equals("ABSENT")) {
            amount = per_day_salary;
        }

        if (type.equals("HALF_DAY")) {
            amount = per_day_salary / 2;
        }

        if (type.equals("HOURLY_DEDUCTION")) {
            amount = (per_day_salary / 10) * hours;
        }

        return amount;
    }

    public List<UserCashCollection> userCashCollectionReport() {
        List<User> users = userRepo.findAll();
        List<UserCashCollection> userCashList = new ArrayList<>();
        for (User user : users) {
            UserCashCollection user_cash_collection = new UserCashCollection();
            List<Billing> bills = billRepo.findByUserIdAndBillDate(user.getUserId(), new Date());

            int user_cash_collected = bills.stream()
                    .mapToInt(Billing::getFinal_amount)
                    .sum();
            user_cash_collection.setUserId(user.getUserId());
            user_cash_collection.setCollection_date(new Date());
            user_cash_collection.setUserName(user.getSname());
            user_cash_collection.setCash_collection(user_cash_collected);
            userCashList.add(user_cash_collection);

        }
        userCashCollectionRepo.saveAll(userCashList);
        return userCashList;

    }

    public List<UserMonthlySalary> generateUserMonthlySalaries(String month_fy) {
        List<UserMonthlySalary> salaries = new ArrayList<>();
        List<User_Salary> salary_statement;
        List<User> users = userRepo.findAll();

        String[] parts = month_fy.split("_");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        Month monthEnum = Month.of(month);
        String monthName = monthEnum + "";
        month_fy = monthName + " " + year;

        for (User user : users) {
            UserMonthlySalary salary = new UserMonthlySalary();
            salary_statement = userSalaryRepo.findUserSalaryByYearMonth(user.getUserId(), year, month);
            int advanceSalary = sumAmountsByType(salary_statement, "ADVANCE");
            int absentSalaryDeduction = sumAmountsByType(salary_statement, "ABSENT");
            int salaryHalfDay = sumAmountsByType(salary_statement, "HALF_DAY");
            int salaryHourlyDeduction = sumAmountsByType(salary_statement, "HOURLY_DEDUCTION");
            int totalSalaryDeducted = absentSalaryDeduction + salaryHalfDay + salaryHourlyDeduction;
            int totalAmount = user.getMonthly_salary() - totalSalaryDeducted - absentSalaryDeduction; // calcualting salaries
            salary.setUserId(user.getUserId());
            salary.setUser_name(user.getSname());
            salary.setMonth_fy(month_fy);
            salary.setSalaryDeducted(totalSalaryDeducted);
            salary.setAdvanceSalary(advanceSalary);
            salary.setFinalAmount(totalAmount);
            salary.setMonthlySalary(user.getMonthly_salary());
           
            UserMonthlySalaryId id=new UserMonthlySalaryId(user.getUserId(),month_fy);
            System.out.println("Id: "+id);
            Optional<UserMonthlySalary> opt=userMonthlySalaryRepository.findById(id);
            if(opt.isPresent())
            {
               UserMonthlySalary userMonthlySalary=opt.get();
               salary.setStatus(userMonthlySalary.getStatus());
            }
            
            else{
            salary.setStatus("PENDING");
            }
            
            salaries.add(salary);
        }

        userMonthlySalaryRepository.saveAll(salaries);
        return salaries;
    }

    
    public String userMonthlySalaryChangeStatus(UserMonthlySalary salary)
    {
         try{
             System.out.println(salary);
             salary.setStatus("PAID");
             userMonthlySalaryRepository.save(salary);
             return "Salary updated";
         
         }catch(Exception e)
         {
            e.printStackTrace();
            return "Unable to update status";
         }
    }
            
    
    
    public int sumAmountsByType(List<User_Salary> salaries, String type) {
        return salaries.stream()
                .filter(salary -> type.equals(salary.getType()))
                .mapToInt(User_Salary::getAmount)
                .sum();
    }

}
