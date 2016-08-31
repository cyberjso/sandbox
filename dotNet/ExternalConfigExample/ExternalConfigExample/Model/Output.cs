using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ExternalConfigExample.Model
{
    public class Output
    {
        public List<String> AppSettings { get; set; }
        public List<String> ConnectionStrings { get; set; }

        internal void setAppSettings(List<string> list)
        {
            throw new NotImplementedException();
        }
    }

}