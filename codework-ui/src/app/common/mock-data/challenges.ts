import { ChallengeService } from "src/app/component/challenge/view-challenge/challenge.service";
import { Challenge, ChallengeStatus, ParticipationStatus } from "src/app/model/challenge.model";

let challengeList : Array<Challenge> = [
    {
        id : 1,
        name : 'Code Challenge 1',
        shortDescription : 'Challenge description',
        startDate : new Date("2021-10-01T00:00:00"),
        endDate : new Date("2021-10-16T00:00:00"),
        bannerImage : 'https://picsum.photos/200/150/?random',   
        isRegistered : false,
        longDescription : '<h1 class="display-5">Hello, world!</h1> <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p><p>It uses utility classes for typography and spacing to space content out within the larger container.</p>',
        status : ChallengeStatus.SCHEDULED,
        participationStatus : ParticipationStatus.NOT_STARTED
    },
    {
        id : 2,
        name : 'Code Challenge 2',
        shortDescription : 'Challenge description',
        startDate : new Date("2021-10-03T00:00:00"),
        endDate : new Date("2021-12-03T00:00:00"),
        bannerImage : 'https://picsum.photos/200/150/?random',
        isRegistered : true,
        longDescription : '<h1 class="display-5">Hello, world!</h1> <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p><p>It uses utility classes for typography and spacing to space content out within the larger container.</p>',
        status : ChallengeStatus.LIVE,
        participationStatus : ParticipationStatus.STARTED
    },
    {
        id : 3,
        name : 'Code Challenge 3',
        shortDescription : 'Challenge description',
        startDate : new Date("2021-11-01T00:00:00"),
        endDate : new Date("2021-11-30T00:00:00"),
        bannerImage : 'https://picsum.photos/200/150/?random',
        isRegistered : false,
        longDescription : '<h1 class="display-5">Hello, world!</h1> <p class="lead">This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p><p>It uses utility classes for typography and spacing to space content out within the larger container.</p>',
        status : ChallengeStatus.LIVE,
        participationStatus : ParticipationStatus.NOT_STARTED
    }
]

export { challengeList }

