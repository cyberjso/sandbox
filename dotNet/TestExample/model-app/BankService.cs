using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace io.sandbox.examples.csharp
{
    public class BankService
    {
        private ExternalService ExternalService;
        private Validator Validator;

        public BankService(ExternalService ExternalService, Validator Validator)
        {
            this.ExternalService = ExternalService;
            this.Validator = Validator;
        }

        public Account WithDraw(Account debitedAccount, Double ammount)
        {
            if (!Validator.IsValid(debitedAccount)) throw new BankServiceException();

            return BuildAccount(debitedAccount, callExternalService(debitedAccount, ammount));
        }

        private Account BuildAccount(Account debitedAccount, TransactionReply reply)
        {
            Account account = new Account();
            account.Id = reply.AccountId;
            account.Description = debitedAccount.Description;
            account.Ammount = reply.UpdatedAmmount;
            account.IsBlocked = account.IsBlocked;
            return account;
        }

        private TransactionReply callExternalService(Account debitedAccount, Double ammount)
        {
            try
            {
                return  ExternalService.Debit(new Transaction(debitedAccount.Id, ammount));
            }
            catch (Exception e)
            {
                throw new BankServiceException();
            }
        }

    }

    public class BankServiceException : Exception 
    {
        public override string Message
        {
            get
            {
                return "BankService was not able to complete Its job";
            }
        }

    }

    public class Account
    {
        public String Id { get; set; }
        public String Description { get; set; }
        public Double Ammount { get; set; }
        public Boolean IsBlocked { get; set; }
    }
}
