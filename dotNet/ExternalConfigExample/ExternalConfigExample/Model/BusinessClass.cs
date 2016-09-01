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
            output.ConnectionStrings = getConnectionStrings();
            return output;
        }

        private List<String> getAppSettings() 
        {
            return new List<String>() { 
                AppSettings("Propriedade1"), 
                AppSettings("Propriedade2"), 
                AppSettings("Propriedade3") };
        }

        private List<String> getConnectionStrings()
        {
            return new List<String>() { 
                ConnectionString("Propriedade4"), 
                ConnectionString("Propriedade5"), 
                ConnectionString("Propriedade6") };
        }

        private String ConnectionString(String name) { return WebConfigurationManager.ConnectionStrings[name].ConnectionString; }

        private String AppSettings(String name) { return WebConfigurationManager.AppSettings[name]; }
    }
}