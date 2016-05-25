using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MVPExample.View;

namespace MVPExample.Presenters
{
    public class HelloPresenter
    {
        private IHelloView HelloView;

        public HelloPresenter(IHelloView HelloView)
        {
            this.HelloView = HelloView;
        }

        public void Send() {
            HelloView.UpdateView(HelloView.Id);            
        }   
 
    }
}