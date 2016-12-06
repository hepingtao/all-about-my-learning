# Git Pull vs Git Fetch (and Stashing)

> Git is one of, if not the, most popular and most widely used source code management systems. In this short post, we’ll explore differences between `git pull` and `git fetch` and talk about __stashing__.

> This post assumes that you are familiar with the basic operation of Git.

1. Difference Between `git pull` and `git fetch`  
  `git pull` will download latest changes from the remote repository and __automatically merge__ those changes in the repository. It does not give you a chance to review the changes and as a result conflicts might occur (and they often do). One important thing to keep in mind is that `git pull` __will merge only into the current working branch__. Other branches will stay unaffected.
  If you only want to download the latest changes and review them before merging or want to merge at a later time, `git fetch` is your friend. Like `git pull`, it will download latest changes. Unlike `git pull`, it will not merge those changes. You might wonder where the changes are stored since they are not merged. The answer is that they are stored in your local repository in what are called `remote tracking branches`. A remote tracking branch is a local copy (or mirror) of a remote branch. For example, when you run `git branch -a` you might notice `origin/master` in the output which is the remote tracking branch for `master`.
  __In short:__ Both are similar with one key difference: `git fetch` only download latest changes where as `git pull` also merges them.

2. Quick Look at Stashing  
  Let's say you are in the middle of implementing a new feature and you need to switch branches to fix a bug or revert back to where you started in the current branch. You don't want to commit half-done work or lose your changes. `git stash` is a handy feature for these types of situations. It takes your changes, saves them a temporary place and cleans up your working directory. This allows you to switch to other branches or work elsewhere. Let's look at some examples:  
  
    To stash your work in progress:
  
    ```bash
    $ echo "Improvement 1 of 3" >> file.txt
    $ git status
    On branch master
    Your branch is ahead of 'origin/master' by 1 commit.
              modified:   file.txt
    $ git stash save "Partial improvement to file.txt"   
    $ git status
    # On branch master
    nothing to commit, working directory clean
    ```
    Once you stash, you are free to switch branches. When you are ready to resume working on the changes you _stashed_, you can `apply` the stash:
    ```bash
    $ git stash apply
    ```
    You can stash multiple times. Use `git stash list` command to see the list of stashes stored on the stash stack.
    
    ```bash
    $ git stash list
    stash@{0}: WIP on master: d724198 partial improvement 2
    stash@{1}: WIP on master: d724198 bug fix for Unity
    stash@{2}: WIP on master: c9a03f4 added partial improvement 1
    ```
    
    `git stash apply` restores the most recent save, `stash@{0}` in this case. To restore one of the older stashes, specify its name e.g. `git stash apply stash@{2}`.
     
     A stash could be applied to any branch not just the same branch it was saved from.
     
     __Note:__ Stash will ignore 'un-tracked' files. If you added a new file, you must first add it to the index using `git add` before stashing.

# Bonus: Useful Git Tips

1. __Deleting Remote Branches__  
  To delete a remote branch named `feature`:
    ```git
    $ git push origin --delete `feature`
    ```
    __Bonus:__ `git branch -d` deletes a local branch. Use `-D` instead of `-d` to force delete a local branch without checking its merge status.

2. __Fixing the Commit Message Before `git push`__
  I often notice a typo or a mistake in my commit message after I commit. Luckily, Git has a way for fix this problem.
    ```git
    $ git commit --amend -m 'New commit message'
    ```
    To bring up the message editor, don't provide the `-m` parameter:
    ```git
    $ git commit --amend
    ```
    Keep in mind that it will not just modify the commit message but also commit any new changes that you might have. This __will not work if you have pushed the commit__ to remote repository.