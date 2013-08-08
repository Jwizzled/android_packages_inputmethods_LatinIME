/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.inputmethod.latin.personalization;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * This class is a session where a data provider can communicate with a personalization
 * dictionary.
 */
public abstract class PersonalizationDictionaryUpdateSession {
    /**
     * This class is a parameter for a new unigram or bigram word which will be added
     * to the personalization dictionary.
     */
    public static class PersonalizationLanguageModelParam {
        public final String mWord0;
        public final String mWord1;
        public final boolean mIsValid;
        public final int mFrequency;
        public PersonalizationLanguageModelParam(String word0, String word1, boolean isValid,
                int frequency) {
            mWord0 = word0;
            mWord1 = word1;
            mIsValid = isValid;
            mFrequency = frequency;
        }
    }

    // TODO: Use a dynamic binary dictionary instead
    public WeakReference<DynamicPredictionDictionaryBase> mDictionary;

    public abstract void onDictionaryReady();

    public void setDictionary(DynamicPredictionDictionaryBase dictionary) {
        mDictionary = new WeakReference<DynamicPredictionDictionaryBase>(dictionary);
    }

    public void addToPersonalizationDictionary(
            final ArrayList<PersonalizationLanguageModelParam> lmParams) {
        final DynamicPredictionDictionaryBase dictionary = mDictionary == null
                ? null : mDictionary.get();
        if (dictionary == null) {
            return;
        }
        for (final PersonalizationLanguageModelParam lmParam : lmParams) {
            dictionary.addToPersonalizationPredictionDictionary(
                    lmParam.mWord0, lmParam.mWord1, lmParam.mIsValid);
        }
    }
}