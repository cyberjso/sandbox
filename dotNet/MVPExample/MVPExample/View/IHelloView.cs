using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace MVPExample.Presenters
{
    public interface IHelloView
    {
        String Id { get; }
        void UpdateView(String value);
    }

}
