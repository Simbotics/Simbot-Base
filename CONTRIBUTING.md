# Preface
This project is intended to be used by Simbotics, as well as any other teams who wish to implement this template for their FRC team needs. This repository may be seen and used by a wide array of people from all backgrounds. Please keep this in mind when submitting issues or pull requests (PRs), and when interacting with others in this repository.

# Contributing
Want to contribute to this project? We make it as simple as possible to open a pull request and contribute to the codebase to make it the best it can. To open a pull request and contribute code, it's easy! Just follow these steps:

1. Create an [issue](https://github.com/Simbotics/Simbot-Base/issues) on our Github. This ensures that work isn't being duplicated and proposed contributions are approved by Simbotics members. If you intend to implement the proposal, leave a comment indicating so.
2. Once your issue is approved and assigned to you, clone [this repository](https://github.com/Simbotics/Simbot-Base) and develop on a new branch. Please name your branches in accordance with our [branch naming guidelines](#branch-naming).
3. When ready, submit a pull request by going to [the pull request section](https://github.com/Simbotics/2023-Simbot/pulls) in our repository and click the `new pull request` button on the right side of your screen. A template should pre-populate in the pull request screen, please use it.
4. Finally, once you have reviewed your contributions just hit the `create pull request` button and you're all set!

_Note: Please await the review of one or more repository managers until your code changes are merged to main._ **ALL GITHUB ACTIONS MUST PASS IN A PULL REQUEST TO BE ELIGIBLE TO BE MERGED**

# Development Standards and Guidelines

## Branch Naming

Branch names should follow the format `{category}-{issue_id}-{description}`, where categories are:
|Category|Description|
|-----|-----|
|bugfix|for fixing a bug|
|feature|for adding, removing or modifying a feature|
|docs|for adding, removing or modifying documentation|
|test|for experimenting with something which is not an issue|

Ensure to use hyphens to separate words in your branch name. Example: `feature-37-add-drive-subsystem`
## Pull Request Guidelines
In short, follow [good code review principles](https://dev.to/karaluton/a-guide-to-perfecting-pull-requests-2b66) when submitting pull requests. In summary:
* Include _what_ changes are made in the PR, _why_ you made any decisions you did, and _how_ the changes were tested/verified and how the reviewer can do the same.
* Please ensure your code is well-documented. Expect this project to be read by students who have never written code before, let alone robot code.
* Keep PRs small. The ideal size is under 150 lines of code, and anything over 400 lines may be rejected outright, and you may be asked to split the problem into more manageable subtasks.
* Self-review your PR before submitting. Looks for performance improvements, missing test cases, readability improvements, good/bad comments and variable names, and similar general code quality improvements that could be made.

### Commit guidelines
* Please use [conventional commit](https://www.conventionalcommits.org/en/v1.0.0/) messages. We won't hate you if you don't, but it is greatly appreciated by reviewers.
* Keep commits small and atomic. You should be able to roll back a single commit and have working code on either side of the roll back. Each commit should represent a single change.
  * Tip: If your commit message includes the word "and", it might be able to be split into 2 commits
    * This doesn't apply to messages like "update feature and tests" where changing the feature breaks the old tests -- use your best judgement

## Formatting and Linting
* We use `google-java-format` for our Java code. An action will run on pull request creation to automatically format your code to meet this specification if it doesn't already.

## Testing
* Please write unit tests for your code. This is especially true in breaking changes, or changes that fix a (testable) bug.
* TODO [add unit test framework & how-to guide](https://github.com/Simbotics/Simbot-Base/issues/3)
