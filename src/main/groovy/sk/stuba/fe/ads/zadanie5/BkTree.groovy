package sk.stuba.fe.ads.zadanie5

import static org.apache.commons.lang3.StringUtils.getLevenshteinDistance

class BkTree {

    BkNode root = null

    public void add(String word) {
        if (!root) {
            root = new BkNode(word)
        } else {
            BkNode node = root;
            while (node.word != word) {
                int distance = getLevenshteinDistance(node.word, word)
                BkNode parent = node
                node = parent.getChild(distance)
                if (!node) {
                    node = new BkNode(word)
                    parent.children.put(distance, node)
                    break
                }
            }
        }
    }

    public WordMatch findBest(String search) {
        Set<WordMatch> matches = find(search, optimalDistance(search))
        WordMatch bestMatch = null
        int bestDistance = Integer.MAX_VALUE
        for (WordMatch match : matches) {
            if (match.distance < bestDistance) {
                bestMatch = match
                bestDistance = match.distance
            }
        }
        return bestMatch
    }

    private static int optimalDistance(String word) {
        return word.length() / 3
    }

    public Set<WordMatch> find(String search, int maxDistance) {
        Set<WordMatch> matches = new HashSet<>()
        Queue<BkNode> queue = new ArrayDeque<>()
        queue.add(root)
        while (!queue.isEmpty()) {
            BkNode node = queue.remove()
            String word = node.word
            int distance = getLevenshteinDistance(search, word)
            if (distance <= maxDistance) {
                matches.add(new WordMatch(word, distance))
                if (distance == 0) {
                    break
                }
            }

            int minSearchDistance = Math.max(distance - maxDistance, 0)
            int maxSearchDistance = distance + maxDistance

            for (int searchDistance = minSearchDistance; searchDistance <= maxSearchDistance; ++searchDistance) {
                BkNode child = node.getChild(searchDistance)
                if (child) {
                    queue.add(child)
                }
            }
        }
        return matches
    }

    private class BkNode {
        final String word
        final Map<Integer, BkNode> children

        BkNode(String word) {
            this.word = word
            this.children = new HashMap<>()
        }

        public BkNode getChild(int distance) {
            return children.get(distance)
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            BkNode node = (BkNode) o

            if (children != node.children) return false
            if (word != node.word) return false

            return true
        }

        int hashCode() {
            int result = word.hashCode()
            result = 31 * result + children.hashCode()
            return result
        }
    }

    public class WordMatch {
        final String word
        final int distance

        WordMatch(String word, int distance) {
            this.word = word
            this.distance = distance
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            WordMatch match = (WordMatch) o

            if (distance != match.distance) return false
            if (word != match.word) return false

            return true
        }

        int hashCode() {
            int result
            result = word.hashCode()
            result = 31 * result + distance
            return result
        }
    }
}
