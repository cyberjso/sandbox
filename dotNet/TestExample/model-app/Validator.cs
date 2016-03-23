using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace io.sandbox.examples.csharp
{
    
    public class Validator
    {
        public Boolean IsValid(Account account)
        {
            if (account.Id == null || account.Id.Trim().Equals("")) return false;
            if (account.Description == null || account.Description.Trim().Equals("")) return false;
            if (account.Ammount == null) return false;
            if (account.IsBlocked) return false;

            return true;
        }


    }
}
