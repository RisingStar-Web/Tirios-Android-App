package com.ai.tirios.services.module

import com.ai.tirios.ui.image_viewer.ImageViewerActivity
import com.ai.tirios.ui.image_viewer.ImageViewerModule
import com.ai.tirios.ui.add_bank_accounts.AddBankAccountsActivity
import com.ai.tirios.ui.add_bank_accounts.AddBankAccountsModule
import com.ai.tirios.ui.add_properties.AddPropertyFragmentProvider
import com.ai.tirios.ui.add_tenant.AddTenantFragmentProvider
import com.ai.tirios.ui.add_tenant_bot.AddTenantBotActivity
import com.ai.tirios.ui.add_tenant_bot.AddTenantBotModule
import com.ai.tirios.ui.bank_accounts.BankAccountsActivity
import com.ai.tirios.ui.bank_accounts.BankAccountsModule
import com.ai.tirios.ui.change_password.ChangePasswordActivity
import com.ai.tirios.ui.change_password.ChangePasswordModule
import com.ai.tirios.ui.checkout_prices.CheckoutPricesActivity
import com.ai.tirios.ui.checkout_prices.CheckoutPricesModule
import com.ai.tirios.ui.contact_us.ContactUsActivity
import com.ai.tirios.ui.contact_us.ContactUsModule
import com.ai.tirios.ui.credit_card.add_card.AddCardActivity
import com.ai.tirios.ui.credit_card.AddCardModule
import com.ai.tirios.ui.credit_card.MyCardDetailsActivity
import com.ai.tirios.ui.credit_card.MyCardModule
import com.ai.tirios.ui.error_message.ErrorMessageActivity
import com.ai.tirios.ui.error_message.ErrorMessageModule
import com.ai.tirios.ui.forgot_password.ForgotPasswordActivity
import com.ai.tirios.ui.forgot_password.ForgotPasswrodModule
import com.ai.tirios.ui.home.HomeFragmentProvider
import com.ai.tirios.ui.invoice.InvoiceFragmentProvider
import com.ai.tirios.ui.landlord_login.LandLordLoginActivity
import com.ai.tirios.ui.landlord_login.LandLordModule
import com.ai.tirios.ui.main.MainActivity
import com.ai.tirios.ui.main.MainModule
import com.ai.tirios.ui.maintenance.MaintenanceFragmentProvider
import com.ai.tirios.ui.maintenance_bot.MaintenanceBotActivity
import com.ai.tirios.ui.maintenance_bot.MaintenanceBotModule
import com.ai.tirios.ui.my_profile.MyProfileModule
import com.ai.tirios.ui.maintenance_details.MaintenanceDetailsFragmentProvider
import com.ai.tirios.ui.my_profile.MyProfileActivity
import com.ai.tirios.ui.navigation.NavigationActivity
import com.ai.tirios.ui.navigation.NavigationModule
import com.ai.tirios.ui.notifications.NotificationActivity
import com.ai.tirios.ui.notifications.NotificationModule
import com.ai.tirios.ui.owned.OwnedFragmentProvider
import com.ai.tirios.ui.properties.PropertiesFragmentProvider
import com.ai.tirios.ui.property_details.PropertyDetailsFragmentProvider
import com.ai.tirios.ui.register.RegisterActivity
import com.ai.tirios.ui.register.RegisterModule
import com.ai.tirios.ui.rented.RentedFragmentProvider
import com.ai.tirios.ui.select_payment.SelectPaymentFragmentProvider
import com.ai.tirios.ui.send_otp.SendOtpActivity
import com.ai.tirios.ui.send_otp.SendOtpModule
import com.ai.tirios.ui.setting.tnc.TermsAndPolicyActivity
import com.ai.tirios.ui.setting.SettingsActivity
import com.ai.tirios.ui.setting.SettingsModule
import com.ai.tirios.ui.signup.SignUpActivity
import com.ai.tirios.ui.signup.SignUpModule
import com.ai.tirios.ui.signupchatbot.SignUpChatBotActivity
import com.ai.tirios.ui.signupchatbot.SignUpChatBotModule
import com.ai.tirios.ui.splash.SplashActivity
import com.ai.tirios.ui.splash.SplashModule
import com.ai.tirios.ui.tenant_property_details.TenantDetailsFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Maruthi Ram Yadav on 10/9/2020.
 */

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun bindSplash(): SplashActivity

    @ContributesAndroidInjector(modules = [CheckoutPricesModule::class])
    internal abstract fun bindCheckoutPrices(): CheckoutPricesActivity

    @ContributesAndroidInjector(modules = [NavigationModule::class])
    internal abstract fun bindNavigation(): NavigationActivity

    @ContributesAndroidInjector(modules = [SignUpModule::class])
    internal abstract fun bindSignUp(): SignUpActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    internal abstract fun bindSetting(): SettingsActivity

    @ContributesAndroidInjector(modules = [SettingsModule::class])
    internal abstract fun bindTerms(): TermsAndPolicyActivity

    @ContributesAndroidInjector(modules = [SendOtpModule::class])
    internal abstract fun bindSendOtp(): SendOtpActivity

    @ContributesAndroidInjector(modules = [RegisterModule::class])
    internal abstract fun bindRegister(): RegisterActivity

    @ContributesAndroidInjector(modules = [LandLordModule::class])
    internal abstract fun bindLoginActivity(): LandLordLoginActivity

    @ContributesAndroidInjector(modules = [SignUpChatBotModule::class])
    internal abstract fun bindSignUpChatActivity(): SignUpChatBotActivity

    @ContributesAndroidInjector(modules = [MaintenanceBotModule::class])
    internal abstract fun bindMaintenanceActivity(): MaintenanceBotActivity

    @ContributesAndroidInjector(modules = [ForgotPasswrodModule::class])
    internal abstract fun bindForgotPassword(): ForgotPasswordActivity

    @ContributesAndroidInjector(modules = [MainModule::class, HomeFragmentProvider::class, MaintenanceFragmentProvider::class,
        PropertiesFragmentProvider::class, RentedFragmentProvider::class, OwnedFragmentProvider::class,
        PropertyDetailsFragmentProvider::class, AddTenantFragmentProvider::class,
        AddPropertyFragmentProvider::class, TenantDetailsFragmentProvider::class,
        MaintenanceDetailsFragmentProvider::class, SelectPaymentFragmentProvider::class, InvoiceFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ErrorMessageModule::class])
    internal abstract fun bindErrorMessage(): ErrorMessageActivity

    @ContributesAndroidInjector(modules = [ChangePasswordModule::class])
    internal abstract fun bindChangePassword(): ChangePasswordActivity

    @ContributesAndroidInjector(modules = [MyProfileModule::class])
    internal abstract fun bindMyProfile():MyProfileActivity

    @ContributesAndroidInjector(modules = [ContactUsModule::class])
    internal abstract fun bindContactus():ContactUsActivity


    @ContributesAndroidInjector(modules = [AddBankAccountsModule::class])
    internal abstract fun bindaddBankAccount(): AddBankAccountsActivity

    @ContributesAndroidInjector(modules = [BankAccountsModule::class])
    internal abstract fun bindBankAccounts(): BankAccountsActivity


    @ContributesAndroidInjector(modules = [AddCardModule::class])
    internal abstract fun bindAddCard(): AddCardActivity

    @ContributesAndroidInjector(modules = [MyCardModule::class])
    internal abstract fun bindMyCard(): MyCardDetailsActivity

    @ContributesAndroidInjector(modules = [NotificationModule::class])
    internal abstract fun bindNotification(): NotificationActivity

    @ContributesAndroidInjector(modules = [AddTenantBotModule::class])
    internal abstract fun bindAddTenantBotActivity(): AddTenantBotActivity

    @ContributesAndroidInjector(modules = [ImageViewerModule::class])
    internal abstract fun bindImageViewerActivity(): ImageViewerActivity
}
