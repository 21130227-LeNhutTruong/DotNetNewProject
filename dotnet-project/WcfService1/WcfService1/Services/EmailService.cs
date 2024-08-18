using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Web;

namespace WcfService1.Services
{
    public class EmailService
    {
        private readonly string _smtpServer = "smtp.gmail.com";
        private readonly int _smtpPort = 587;
        private readonly string _smtpUsername = "21130035@st.hcmuaf.edu.vn";
        private readonly string _appPassword = "zavb yyee dvgl lzrm";
        private static EmailService instance;

        public static EmailService GetInstance()
        {
            if(instance == null) instance = new EmailService();
            return instance;
        }

        public bool SendEmail(string to, string subject, string body)
        {
            try
            {
                var mailMessage = new MailMessage
                {
                    From = new MailAddress(_smtpUsername),
                    Subject = subject,
                    Body = body,
                    IsBodyHtml = true
                };
                mailMessage.To.Add(to);

                var smtpClient = new SmtpClient(_smtpServer, _smtpPort)
                {
                    Credentials = new System.Net.NetworkCredential(_smtpUsername, _appPassword),
                    EnableSsl = true
                };


                smtpClient.Send(mailMessage);
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }
    }
}