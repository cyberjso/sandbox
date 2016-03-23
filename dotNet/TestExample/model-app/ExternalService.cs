using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace io.sandbox.examples.csharp
{
    public interface ExternalService
    {
        TransactionReply Debit(Transaction Input);
    }

    public class Transaction
    {
        public Transaction(String AccountId, Double Ammount)
        {
            this.AccountId = AccountId;
            this.Ammount = Ammount;
        }

        public String AccountId {get; set; }
        public Double Ammount { get; set; }

    }

    public class TransactionReply
    {
        public TransactionReply(String status, String AccountId, Double UpdatedAmmount) 
        {
            this.AccountId = AccountId;
            this.status = status;
            this.UpdatedAmmount = UpdatedAmmount;
        }

        public String status { get; set; }
        public String AccountId { get; set; }
        public Double  UpdatedAmmount{get; set;}
    }

}
