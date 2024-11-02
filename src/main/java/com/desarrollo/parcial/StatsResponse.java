package com.desarrollo.parcial;

class StatsResponse {
    private long count_mutant_dna;
    private long count_human_dna;
    private double ratio;

    public StatsResponse(long countMutantDna, long countHumanDna, double ratio) {
        this.count_mutant_dna = countMutantDna;
        this.count_human_dna = countHumanDna;
        this.ratio = ratio;
    }

    public long getCountMutantDna() {
        return count_mutant_dna;
    }

    public long getCountHumanDna() {
        return count_human_dna;
    }

    public double getRatio() {
        return ratio;
    }
}
