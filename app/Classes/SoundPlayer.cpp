//
//  SoundPlayer.cpp
//  cluburo
//
//  Created by TakuKobayshi on H29/03/07.
//
//

#include "SoundPlayer.hpp"

USING_NS_CC;

using namespace experimental;

SoundPlayer::SoundPlayer(std::string fileName)
{
    mFilename = fileName;
    mSoundId = AudioEngine::INVALID_AUDIO_ID;
}

SoundPlayer::~SoundPlayer(){
}

SoundPlayer* SoundPlayer::create(std::string fileName)
{
    auto soundPlayer = new SoundPlayer(fileName);
    AudioEngine::preload(fileName);
    return soundPlayer;
}

void SoundPlayer::play(){
    mSoundId = AudioEngine::play2d(mFilename.c_str());
    mPlaying = true;
    AudioEngine::setVolume(mPlaying, mVolume);
}

int SoundPlayer::getSoundId(){
    return mSoundId;
}

void SoundPlayer::stop(){
    AudioEngine::stop(mSoundId);
    mPlaying = false;
}

void SoundPlayer::pause(){
    AudioEngine::pause(mSoundId);
    mPlaying = false;
}

bool SoundPlayer::isPlaying(){
    return mPlaying;
}

float SoundPlayer::getDuration(){
    if (mSoundId == AudioEngine::INVALID_AUDIO_ID) {
        return 0;
    }
    return AudioEngine::getDuration(mSoundId);
}

float SoundPlayer::getCurrentTime(){
    if (mSoundId == AudioEngine::INVALID_AUDIO_ID) {
        return 0;
    }
    return AudioEngine::getCurrentTime(mSoundId);
}

void SoundPlayer::setVolume(float vol){
    mVolume = vol;
    if (mPlaying) {
        AudioEngine::setVolume(mSoundId, vol);
    }
}

float SoundPlayer::getVolume(){
    return mVolume;
}

void SoundPlayer::setLoop(bool loop){
    mPlaying = loop;
    if (mPlaying) {
        AudioEngine::setLoop(mSoundId, loop);
    }
}

bool SoundPlayer::isLoop(){
    return mLoop;
}
