using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using System;

namespace io.sandbox.examples.csharp
{
    [TestClass]
    public class BankServiceTest
    {
        private BankService BankService;
        private Mock<ExternalService> ExternalService;

        public BankServiceTest()
        {
            this.ExternalService = new Mock<ExternalService>();
            this.BankService = new BankService(this.ExternalService.Object, new Validator());
        }

        [TestMethod]
        public void ShouldCompleteTransactionWhenThereIsNoError()
        {
            this.ExternalService.Setup(m => m.Debit(It.IsAny<Transaction>()))
                .Returns(new TransactionReply("ok", "1", 0D));

            Account account = BuildAccount();
            Account result = BankService.WithDraw(account, 1D);
            Assert.AreEqual("1", result.Id);
            Assert.AreEqual(0D, result.Ammount);
            Assert.AreEqual("blalabla", result.Description);
            Assert.IsFalse(account.IsBlocked);
        }

        [TestMethod()]
        [ExpectedException(typeof(BankServiceException))]
        public void ShouldHandleExceptionsThrownByExternalService()
        {
            this.ExternalService.Setup(m => m.Debit(It.IsAny<Transaction>())).Throws<SystemException>();
            BankService.WithDraw(BuildAccount(), 1D);
        }

        private Account BuildAccount()
        {
            Account account = new Account();
            account.Id = "1";
            account.IsBlocked = false;
            account.Ammount = 1D;
            account.Description = "blalabla";
            return account;
        }

    }
}
