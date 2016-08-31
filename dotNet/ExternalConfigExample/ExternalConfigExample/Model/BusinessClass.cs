using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;

namespace ExternalConfigExample.Model
{
    public class BusinessClass
    {
        public Output DoSomething()
        {
            Output output = new Output();
            output.AppSettings = getAppSettings();
            return output;
        }

        private List<String> getAppSettings() 
        {
            return new List<String>() { 
                property("Propriedade1"), 
                property("Propriedade2"), 
                property("Propriedade3") };
        }

        private String property(String name) { return WebConfigurationManager.AppSettings[name]; }
    }
}