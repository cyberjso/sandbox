using io.sandbox.examples.csharp;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;

namespace io.sandbox.examples.csharp
{
        
    [TestClass()]
    public class ValidatorTest
    {
        private Validator validator = new Validator();

        [TestMethod()]
        public void ShouldValidateWhenNoIdIsNotSet()
        {
            Account accout = new Account();
            accout.Description = "description";
            accout.Ammount = 0D;
            Assert.IsFalse(validator.IsValid(accout));
        }


        [TestMethod()]
        public void ShouldValidateWhenDesciptionIsNotSet()
        {
            Account account = new Account();
            account.Id = "1";
            account.Ammount = 0D;
            Assert.IsFalse(validator.IsValid(account));
        }

        [TestMethod()]
        public void ShouldNotValidateWhenEverythingIsFine()
        {
            Account account = new Account();
            account.Id = "1";
            account.Description = "something";
            account.Ammount = 0D;
            Assert.IsTrue(validator.IsValid(account));
        }

    }

}
