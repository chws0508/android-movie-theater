package woowacourse.movie.presenter

import domain.Reservation
import woowacourse.movie.contract.ReservationListContract
import woowacourse.movie.mock.MockReservationsFactory
import woowacourse.movie.model.mapper.ReservationMapper.toUi

class ReservationListPresenter(val view: ReservationListContract.View) :
    ReservationListContract.Presenter {

    override fun showReservationDetail(reservation: Reservation) {
        val reservationUiModel = reservation.toUi()
        val movieUiModel = reservationUiModel.movie
        val ticketsUiModel = reservationUiModel.tickets
        view.startReservationResultActivity(movieUiModel, ticketsUiModel)
    }

    override fun getReservationList() {
        val reservationList = MockReservationsFactory.makeReservations()
        view.setAdapter(reservationList)
    }
}
