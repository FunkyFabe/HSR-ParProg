using System.Threading;

namespace BankAccount
{
    public class BankAccount
    {
        private int _balance = 0;

        public int Balance => Volatile.Read(ref _balance);

        public void Deposit(int amount)
        {
            Interlocked.Add(ref _balance, amount);
        }

        public bool Withdraw(int amount)
        {
            int initialValue, computedValue;
            do
            {
                initialValue = Balance;
                if (initialValue < amount)
                { 
                    return false;
                }

                computedValue = initialValue - amount;
            } while (initialValue != Interlocked.CompareExchange(ref _balance, computedValue, initialValue));

            return true;
        }
    }
}