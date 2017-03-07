//
//  SoundPlayer.hpp
//  cluburo
//
//  Created by TakuKobayshi on H29/03/07.
//
//

#ifndef SoundPlayer_hpp
#define SoundPlayer_hpp

#include "cocos2d.h"
#include "audio/include/AudioEngine.h"

class SoundPlayer : public cocos2d::Ref{
private:
    SoundPlayer(std::string filePath);
    int mSoundId;
    std::string mFilename;
    bool mPlaying = false;
    bool mLoop = false;
    float mVolume = 1.0;
public:
    static SoundPlayer* create(std::string filePath);
    
    virtual void load();
    virtual void play();
    virtual void pause();
    virtual void stop();
    virtual int getSoundId();
    virtual float getCurrentTime ();
    virtual float getDuration();
    virtual void setVolume (float volume);
    virtual float getVolume ();
    virtual void setLoop (bool loop);
    virtual bool isLoop ();
    bool isPlaying();
    ~SoundPlayer();
};

#endif /* SoundPlayer_hpp */
