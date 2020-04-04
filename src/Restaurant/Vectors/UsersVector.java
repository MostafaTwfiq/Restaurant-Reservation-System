package Restaurant.Vectors;

import Restaurant.Useres.Clients.Client;
import Restaurant.Useres.Clients.VipClient;
import Restaurant.Useres.Cooker;
import Restaurant.Useres.Manager;
import Restaurant.Useres.Users;
import Restaurant.Useres.Waiter;

public final class UsersVector extends MyVector<Users>{

    public UsersVector(int length){
        super(length);
    }

    public UsersVector(){
        super();
    }

    public int find(String userName, String password){
        for (int i = 0; i < count; i++){
            if (((Users)arr[i]).getUserName().equals(userName)
                    && ((Users)arr[i]).getPassword().equals(password))
                return i;
        }
        return -1;
    }

    public UsersVector getMangersVector(){
        UsersVector managers = new UsersVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Users) arr[i]).getRole().equals("Manager")) {
                managers.add((Manager) arr[i]);
            }
        }
        return managers;
    }

    public UsersVector getCookersVector(){
        UsersVector cookers = new UsersVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Users) arr[i]).getRole().equals("Cooker"))
                cookers.add((Cooker) arr[i]);
        }
        return cookers;
    }

    public UsersVector getWaitersVector(){
        UsersVector waiters = new UsersVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Users) arr[i]).getRole().equals("Waiter"))
                waiters.add((Waiter) arr[i]);
        }
        return waiters;
    }

    public UsersVector getClientsVector(){
        UsersVector clients = new UsersVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Users) arr[i]).getRole().equals("Client"))
                clients.add((Client) arr[i]);
        }
        return clients;
    }

    public UsersVector getVipClientsVector(){
        UsersVector vipClients = new UsersVector(count/2);
        for (int i = 0; i < count; i++) {
            if (((Users) arr[i]).getRole().equals("VipClient"))
                vipClients.add((VipClient) arr[i]);
        }
        return vipClients;
    }

    @Override
    public int findObj(Object obj){
        return find(((Users)obj).getUserName(), ((Users)obj).getPassword());
    }

    @Override
    public Users get(int index){
        if (index < 0 || index >= count)
            throw new NullPointerException();

        return (Users) arr[index];
    }
}
