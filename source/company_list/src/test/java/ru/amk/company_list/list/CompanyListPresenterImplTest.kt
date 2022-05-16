package ru.amk.company_list.list

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.amk.company_list.ActivityView
import ru.amk.company_list.list.interactors.CompanyInteractor
import ru.amk.core.favorite_company.FavoriteCompanyRepositoryCore
import org.junit.Before
import org.junit.Test
import ru.amk.company_list.FavoriteCompany
import ru.amk.company_list.list.handlers.FilterHandler
import ru.amk.company_list.list.handlers.SortHandler
import ru.amk.core.company.Company
import ru.amk.core.utils.SchedulerProvider

class CompanyListPresenterImplTest {

    private lateinit var presenter: CompanyListPresenterImpl

    @MockK
    private lateinit var filterInteractor: CompanyInteractor

    @MockK
    private lateinit var companyListView: CompanyListView

    @MockK
    private lateinit var favoriteCompanyRepositoryCore: FavoriteCompanyRepositoryCore

    @MockK
    private lateinit var activityView: ActivityView

    private val schedulerProvider: SchedulerProvider = object : SchedulerProvider {
        override fun io(): Scheduler = Schedulers.trampoline()

        override fun computation(): Scheduler = Schedulers.trampoline()

        override fun result(): Scheduler = Schedulers.trampoline()
    }

    @RelaxedMockK
    private lateinit var favoriteCompany: FavoriteCompany

    @RelaxedMockK
    private lateinit var company: Company

    @Before
    fun before() {
        MockKAnnotations.init(this)
        presenter = CompanyListPresenterImpl(
            filterInteractor = filterInteractor,
            companyListView = companyListView,
            favoriteCompanyRepositoryCore = favoriteCompanyRepositoryCore,
            activityView = activityView,
            schedulerProvider = schedulerProvider
        )
    }

    @Test
    fun `test onViewCreated success`() {
        val listCompanies = listOf(favoriteCompany)
        every { filterInteractor.getCompanies() } returns Flowable.just(listCompanies)
        justRun { activityView.updateViewsHeader() }
        justRun { companyListView.notifyAllDataChange(any()) }
        justRun { activityView.refreshDone() }

        presenter.onViewCreated()

        verifySequence {
            activityView.updateViewsHeader()
            companyListView.notifyAllDataChange(listOf(favoriteCompany))
            activityView.refreshDone()
        }
    }

    @Test
    fun `test clickSorting success isFavoriteUp is true`() {
        mockkObject(SortHandler)
        justRun { SortHandler.setFavoriteUp(any()) }
        val isFavoriteUp = true

        presenter.clickFavoriteUp(isFavoriteUp)

        verify {
            SortHandler.setFavoriteUp(isFavoriteUp)
        }
    }

    @Test
    fun `test clickSorting success isFavoriteUp is false`() {
        mockkObject(SortHandler)
        justRun { SortHandler.setFavoriteUp(any()) }
        val isFavoriteUp = false

        presenter.clickFavoriteUp(isFavoriteUp)

        verify {
            SortHandler.setFavoriteUp(isFavoriteUp)
        }
    }

    @Test
    fun `test addFavoriteCompanyaddFavoriteCompany success`() {
        justRun { favoriteCompanyRepositoryCore.addFavoriteCompany(any()) }

        presenter.addFavoriteCompany(company = company)

        verify {
            favoriteCompanyRepositoryCore.addFavoriteCompany(company)
        }
    }

    @Test
    fun `test deleteCompanyFromFavorite success`() {
        justRun { favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(any()) }

        presenter.deleteCompanyFromFavorite(company = company)

        verify {
            favoriteCompanyRepositoryCore.deleteCompanyFromFavorite(company)
        }
    }

    @Test
    fun `test filterCompany success`() {
        val filterName = "filterName"
        mockkObject(FilterHandler)
        justRun { FilterHandler.filterCompany(any()) }

        presenter.filterCompany(filterName = filterName)

        verify {
            FilterHandler.filterCompany(query = filterName)
        }
    }
}