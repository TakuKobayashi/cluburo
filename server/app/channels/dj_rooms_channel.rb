class DjRoomsChannel < ApplicationCable::Channel
  def subscribed
    logger.info "subsclibed"
    # stream_from "some_channel"
  end

  def unsubscribed
  	logger.info "unsubsclibed"
    # Any cleanup needed when channel is unsubscribed
  end

  def play
  end
end
